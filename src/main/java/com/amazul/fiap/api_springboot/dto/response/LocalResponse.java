package com.amazul.fiap.api_springboot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de um local ou instalação geográfica")
public record LocalResponse(
        @Schema(description = "Identificador único do local", example = "1")
        Long id,

        @Schema(description = "Nome da instituição, centro, usina ou reator de pesquisa", example = "Complexo radiológico de Iperó/Aramar (CTMSP)")
        String nome,

        @Schema(description = "Cidade onde a instalação está sediada", example = "Iperó", nullable = true)
        String cidade,

        @Schema(description = "Nome por extenso do país retornado pela API de geolocalização", example = "Brazil", nullable = true)
        String nomePais,

        @Schema(description = "Código ISO-2 do país onde está situado", example = "BR", nullable = true)
        String codigoPais,

        @Schema(description = "Latitude geográfica da localização", example = "-23.4075", nullable = true)
        String latitude,

        @Schema(description = "Longitude geográfica da localização", example = "-47.6369", nullable = true)
        String longitude
) {
}
