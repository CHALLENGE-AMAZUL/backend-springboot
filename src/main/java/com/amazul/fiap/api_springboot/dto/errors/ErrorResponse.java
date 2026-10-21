package com.amazul.fiap.api_springboot.dto.errors;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Resposta padronizada para erros de negócio e sistema")
public record ErrorResponse(
        @Schema(description = "Mensagem amigável descrevendo a causa do erro", example = "Artigo de id (999) não encontrado!")
        String mensagem,

        @Schema(description = "Descrição do código de status HTTP em texto", example = "Not Found")
        String erro,

        @Schema(description = "Código de retorno do status HTTP", example = "404")
        int status,

        @Schema(description = "Data/hora em que ocorreu o erro", example = "2026-10-21T01:30:00")
        LocalDateTime timestamp) {
}
