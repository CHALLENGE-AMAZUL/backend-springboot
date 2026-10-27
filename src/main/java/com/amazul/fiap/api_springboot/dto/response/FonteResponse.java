package com.amazul.fiap.api_springboot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

@Schema(description = "DTO de resposta representando uma fonte bibliográfica e científica")
public record FonteResponse(
        @Schema(description = "Identificador único da fonte científica", example = "1")
        Long id,

        @Schema(description = "Título ou nome da publicação/instituição científica", example = "Programa Nuclear da Marinha - CTMSP")
        String titulo,

        @Schema(description = "URL oficial ou DOI da publicação", example = "https://www.marinha.mil.br/ctmsp/programa-nuclear")
        String url,

        @Schema(description = "Lista dos títulos dos artigos que utilizam esta fonte como referência", example = "[\"Propulsão Nuclear Naval no Brasil\"]")
        List<String> artigos,

        @Schema(description = "Data e hora do último registro ou verificação da fonte", example = "2026-10-26T22:00:00.000Z")
        Date atualizadoEm
) {
}
