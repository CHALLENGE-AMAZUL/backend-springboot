package com.amazul.fiap.api_springboot.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Inserção de campos para a relação entre artigo e fonte")
public record ArtigoFonteRequest(
        @NotNull(
                message = "ID da fonte é obrigatório!"
        )
        @Schema(
                description = "ID da fonte bibliográfica cadastrada",
                example = "1"
        )
        Long fonteId,
        @Schema(
                description = "Observação complementar",
                example = "Capítulo 4, Tabela 2 - Decaimento do Mo-99"
        )
        String observacao
) {
}
