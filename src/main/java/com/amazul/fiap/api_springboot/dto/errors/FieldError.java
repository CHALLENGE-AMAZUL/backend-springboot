package com.amazul.fiap.api_springboot.dto.errors;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "FieldError",
        description = "Representa um erro associado a um campo específico da requisição."
)
public record FieldError(
        @Schema(
                description = "Nome do campo inválido.",
                example = "titulo"
        )
        String campo,

        @Schema(
                description = "Mensagem detalhando a violação da regra de validação.",
                example = "O título deve possuir entre 5 e 100 caracteres."
        )
        String mensagem
) {}
