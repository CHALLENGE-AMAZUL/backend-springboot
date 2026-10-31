package com.amazul.fiap.api_springboot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO resumido contendo o nome oficial e código ISO-2 do país retornado pela API externa")
public record PaisCodigoResponse(
        @Schema(description = "Nome oficial do país em inglês", example = "Brazil")
        @JsonProperty("name") String nomePais,

        @Schema(description = "Código de identificação internacional de 2 letras (ISO-2)", example = "BR")
        @JsonProperty("iso2") String codigoPais) {
}
