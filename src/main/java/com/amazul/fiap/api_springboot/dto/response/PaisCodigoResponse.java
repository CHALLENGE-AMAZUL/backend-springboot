package com.amazul.fiap.api_springboot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaisCodigoResponse(@JsonProperty("name") String nomePais,
                                 @JsonProperty("iso2") String codigoPais) {
}
