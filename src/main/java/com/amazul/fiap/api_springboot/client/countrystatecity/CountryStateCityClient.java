package com.amazul.fiap.api_springboot.client.countrystatecity;

import com.amazul.fiap.api_springboot.config.RestClientConfig;
import com.amazul.fiap.api_springboot.dto.response.PaisCodigoResponse;
import com.amazul.fiap.api_springboot.dto.response.PaisDetalhesResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class CountryStateCityClient {
    private final RestClient restClient;
    @org.springframework.beans.factory.annotation.Value("${api.country.key:}")
    private String apiKey;

    private void exigirChave() {
        if (apiKey.isBlank()) throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE, "Integração de países não configurada.");
    }

    public CountryStateCityClient(RestClient countryStateRestClient) {
        this.restClient = countryStateRestClient;
    }

    public List<PaisCodigoResponse> listarPaises(){
        exigirChave();
        return restClient.get()
                .uri("/countries")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public PaisDetalhesResponse buscarPais(String codigoPais){
        exigirChave();
        return restClient.get()
                .uri("/countries/{codigoPais}", codigoPais.toUpperCase())
                .retrieve()
                .body(PaisDetalhesResponse.class);
    }
}
