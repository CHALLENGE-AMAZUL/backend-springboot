package com.amazul.fiap.api_springboot.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
@OpenAPIDefinition(
        info = @Info(
                title = "AMAZUL - Plataforma Educativa de Divulgação Nuclear",
                version = "v1",
                description = "API RESTful do projeto educativo em parceria acadêmica com a AMAZUL e o CTMSP. Disponibiliza conteúdos científicos, mapeamento geográfico de instalações e acervo de referências bibliográficas."
        ),
        servers = {
                @Server(url = "/", description = "Servidor atual")
        }
)
public class OpenApiConfig {
}
