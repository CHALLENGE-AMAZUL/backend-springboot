package com.amazul.fiap.api_springboot.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Challenge SpringBoot - AMAZUL",
                version = "v1",
                description = "API do projeto SpringBoot para o Challenge Fiap 2026-27, que trabalha com registro de artigos referente à tecnologia nuclear."
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Ambiente local")
        }
)
public class OpenApiConfig {
}
