package com.amazul.fiap.api_springboot.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LocalRequest(
        @NotBlank
        String nome,
        String codigoPais) {
}
