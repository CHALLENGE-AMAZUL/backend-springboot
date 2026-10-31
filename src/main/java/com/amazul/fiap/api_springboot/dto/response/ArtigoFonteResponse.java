package com.amazul.fiap.api_springboot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO contendo as relações de artigo e fonte")
public record ArtigoFonteResponse(
        @Schema(description = "Identificador único do vínculo artigo-fonte", example = "1")
        Long id,

        @Schema(description = "Título do artigo associado à referência", example = "Produção de Radiofármacos para Diagnóstico e Tratamento Oncológico")
        String titulo,

        @Schema(description = "URL oficial da fonte científica associada", example = "https://www.marinha.mil.br/ctmsp/programa-nuclear")
        String url,

        @Schema(description = "Observação contextual sobre a referência (ex: capítulo, página ou tabela)", example = "Capítulo 4, Tabela 2 - Decaimento do Mo-99", nullable = true)
        String observacao) {
}
