package com.amazul.fiap.api_springboot.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

@Schema(description = "Dados para registro de fonte ou referência científica")
public record FonteRequest(
        @Schema(description = "Titulo ou nome da publicação ou instituição científica",
                example = "Programa Nuclear da Marinha - CTMSP",
                maxLength = 150,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Título deve ser inserido!")
        @Size(
                max = 150,
                message = "Título deve ter no máximo 150 caracteres"
        )
        String titulo,

        @Schema(description = "Link direto do artigo, publicação técnica ou repositório oficial",
                example = "https://www.marinha.mil.br/ctmsp/programa-nuclear",
                maxLength = 500,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "URL da fonte deve ser inserida!")
        @Size(
                max = 500,
                message = "URL da fonte deve ter no máximo 500 caracteres!"
        )
        @URL(message = "A URL informada deve possuir um formato válido!")
        String url

) {}
