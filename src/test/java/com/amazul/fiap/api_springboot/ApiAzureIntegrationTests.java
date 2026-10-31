package com.amazul.fiap.api_springboot;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.profiles.active=azure", "app.admin.username=editor", "app.admin.password=local-fixture-password-only"})
class ApiAzureIntegrationTests {
    @Autowired Environment environment;
    private final ObjectMapper json = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();
    private static final HttpServer countries = countryFixture();

    // Servidor local para exercitar a integração sem depender do serviço externo.
    private static HttpServer countryFixture() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            server.createContext("/v1/countries", exchange -> {
                boolean us = exchange.getRequestURI().getPath().endsWith("/US");
                byte[] body = (us ? "{\"name\":\"United States\",\"iso2\":\"US\",\"latitude\":\"38\",\"longitude\":\"-97\"}"
                        : "{\"name\":\"Brazil\",\"iso2\":\"BR\",\"latitude\":\"-10\",\"longitude\":\"-55\"}").getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, body.length);
                exchange.getResponseBody().write(body);
                exchange.close();
            });
            server.start();
            return server;
        } catch (Exception e) { throw new IllegalStateException(e); }
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("api.country.base-url", () -> "http://127.0.0.1:" + countries.getAddress().getPort() + "/v1");
        registry.add("api.country.key", () -> "fixture-only");
    }

    @AfterAll static void close() { countries.stop(0); }

    private HttpResponse<String> call(String method, String path, String body, boolean authenticated) throws Exception {
        var builder = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + environment.getProperty("local.server.port") + path))
                .header("Content-Type", "application/json")
                .method(method, body == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body));
        if (authenticated) builder.header("Authorization", "Basic " + Base64.getEncoder().encodeToString(
                "editor:local-fixture-password-only".getBytes(StandardCharsets.UTF_8)));
        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    private String article(String area, String summary) {
        return "{\"titulo\":\"Artigo educativo\",\"resumo\":\"" + summary + "\",\"aplicacaoReal\":\"Aplicacao\",\"impactoSociedade\":\"Impacto\",\"area\":\"" + area + "\"}";
    }

    @Test void publicReadAndProtectedWrite() throws Exception {
        assertEquals(200, call("GET", "/actuator/health", null, false).statusCode());
        assertEquals(200, call("GET", "/swagger-ui/index.html", null, false).statusCode());
        var spec = call("GET", "/v3/api-docs", null, false);
        assertEquals(200, spec.statusCode());
        assertEquals("/", json.readTree(spec.body()).path("servers").get(0).path("url").asText());
        assertEquals(401, call("POST", "/api/artigos", article("SAUDE", "Resumo"), false).statusCode());
        assertNotEquals(200, call("GET", "/h2-console/", null, true).statusCode());
    }

    @Test void acceptsLongSummaryAndUpdatesAreaThenDeletes() throws Exception {
        var created = call("POST", "/api/artigos", article("SAUDE", "a".repeat(500)), true);
        assertEquals(201, created.statusCode(), created.body());
        long id = json.readTree(created.body()).path("id").asLong();
        var updated = call("PUT", "/api/artigos/" + id, article("ENERGIA", "Resumo"), true);
        assertEquals(200, updated.statusCode());
        assertEquals("ENERGIA", json.readTree(updated.body()).path("areaAplicacao").asText());
        assertEquals(204, call("DELETE", "/api/artigos/" + id, null, true).statusCode());
        assertEquals(404, call("GET", "/api/artigos/" + id, null, false).statusCode());
    }

    @Test void badInputReturns400() throws Exception {
        assertEquals(400, call("POST", "/api/artigos", "{", true).statusCode());
        assertEquals(400, call("GET", "/api/artigos?pagina=-1", null, false).statusCode());
        assertEquals(400, call("GET", "/api/artigos?tamanho=101", null, false).statusCode());
        assertEquals(400, call("POST", "/api/artigos", article("SAUDE", "a".repeat(501)), true).statusCode());
    }

    @Test void duplicateSourceReturns409() throws Exception {
        String body = "{\"titulo\":\"Fonte\",\"url\":\"https://example.org/" + UUID.randomUUID() + "\"}";
        assertEquals(201, call("POST", "/api/fontes", body, true).statusCode());
        assertEquals(409, call("POST", "/api/fontes", body, true).statusCode());
    }

    @Test void countryUpdateAndSearchUseLocation() throws Exception {
        var location = call("POST", "/api/locais", "{\"nome\":\"Local fixture\",\"codigoPais\":\"BR\"}", true);
        assertEquals(201, location.statusCode(), location.body());
        long lid = json.readTree(location.body()).path("id").asLong();
        var created = call("POST", "/api/artigos", article("SAUDE", "Resumo").replace("}", ",\"localId\":" + lid + "}"), true);
        assertEquals(201, created.statusCode());
        var changed = call("PUT", "/api/locais/" + lid, "{\"nome\":\"Local fixture\",\"codigoPais\":\"US\"}", true);
        assertEquals(200, changed.statusCode());
        assertEquals("United States", json.readTree(changed.body()).path("nomePais").asText());
        var found = call("GET", "/api/artigos/buscaPorPais?pais=United%20States", null, false);
        assertEquals(200, found.statusCode());
        JsonNode articles = json.readTree(found.body()).path("conteudo");
        assertTrue(articles.size() > 0);
        assertEquals("United States", articles.get(0).path("paisLocal").asText());
    }
}
