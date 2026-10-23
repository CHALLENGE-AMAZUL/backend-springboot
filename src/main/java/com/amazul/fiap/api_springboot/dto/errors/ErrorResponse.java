package com.amazul.fiap.api_springboot.dto.errors;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Resposta padronizada para erros de negócio, recursos não encontrados e falhas internas."
)
public record ErrorResponse(

        @Schema(
                description = "Mensagem amigável descrevendo a causa do erro",
                example = "Artigo de id (999) não encontrado!"
        )
        String mensagem,

        @Schema(
                description = "Descrição textual do status HTTP em retornado",
                example = "Not Found"
        )
        String erro,

        @Schema(
                description = "Código HTTP da resposta.",
                example = "404",
                minimum = "100",
                maximum = "599"
        )
        int status,

        @Schema(
                description = "Data e hora em que o erro ocorreu.",
                example = "2026-10-21T01:30:00",
                type = "string",
                format = "date-time"
        )
        LocalDateTime timestamp
) {}
