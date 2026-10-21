package com.amazul.fiap.api_springboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FonteRequest(
        @NotBlank(message = "Título deve ser inserido!")
        @Size(max = 150, message = "Máximo de 150 caracteres")
        String titulo,

        @NotBlank(message = "URL deve ser inserida!")
        String url

) {

}
