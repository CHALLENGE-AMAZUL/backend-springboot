package com.amazul.fiap.api_springboot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta dos países existentes que contam com conteúdo envolvendo tecnologia nuclear")
public record PaisResponse(
        Long id,

        @Schema(description = "Nome por extenso do país retornado pela API de geolocalização", example = "Brazil", nullable = true)
        String nomePais,

        @Schema(description = "Código ISO-2 do país onde está situado", example = "BR", nullable = true)
        String codigoPais,

        @Schema(description = "Latitude geográfica do país", example = "-23.40", nullable = true)
        String latitude,

        @Schema(description = "Longitude geográfica do país", example = "-47.63", nullable = true)
        String longitude
) { }
