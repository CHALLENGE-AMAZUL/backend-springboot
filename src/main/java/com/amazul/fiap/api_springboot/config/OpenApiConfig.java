package com.amazul.fiap.api_springboot.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "AMAZUL - Plataforma Educativa de Divulgação Nuclear",
                version = "v1",
                description = "API RESTful do projeto educativo em parceria acadêmica com a AMAZUL e o CTMSP. Disponibiliza conteúdos científicos, mapeamento geográfico de instalações e acervo de referências bibliográficas."
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Ambiente de Desenvolvimento Local")
        }
)
public class OpenApiConfig {
}
