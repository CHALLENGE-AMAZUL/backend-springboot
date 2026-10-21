package com.amazul.fiap.api_springboot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO contendo os detalhes geográficos do país")
public record PaisDetalhesResponse(
        @Schema(description = "Nome oficial do país retornado pela API", example = "Brazil")
        @JsonProperty("name") String nomePais,

        @Schema(description = "Código de identificação internacional de 2 letras (ISO-2)", example = "BR")
        @JsonProperty("iso2")  String codigoPais,

        @Schema(description = "Latitude geográfica central do país", example = "-10.00000000")
        String latitude,

        @Schema(description = "Longitude geográfica central do país", example = "-55.00000000")
        String longitude) {
}
