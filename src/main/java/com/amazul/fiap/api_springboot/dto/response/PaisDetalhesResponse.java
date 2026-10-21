package com.amazul.fiap.api_springboot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaisDetalhesResponse(@JsonProperty("name") String nomePais, //name
                                   @JsonProperty("iso2")  String codigoPais, //iso2
                                   String latitude,
                                   String longitude) {
}
