package com.amazul.fiap.api_springboot.dto.errors;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(
        name = "ErrorValidation",
        description = "Resposta retornada quando uma ou mais validações de entrada falham."
)
public record ErrorValidation(
        @Schema(
                description = "Descrição resumida do erro de validação.",
                example = "Erro de validação dos campos da requisição."
        )
        String erro,

        @Schema(
                description = "Código HTTP retornado.",
                example = "400"
        )
        int status,
        @Schema(
                description = "Lista contendo os campos que falharam na validação."
        )
        List<FieldError> campos,

        @Schema(
                description = "Data e hora em que a validação falhou.",
                example = "2026-10-21T01:30:00",
                type = "string",
                format = "date-time"
        )
        LocalDateTime timestamp
) {}
