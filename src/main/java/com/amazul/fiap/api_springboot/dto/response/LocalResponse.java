package com.amazul.fiap.api_springboot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de um local ou instalação geográfica")
public record LocalResponse(Long id,
                            String nome,
                            String cidade,
                            String nomePais,
                            String codigoPais,
                            String latitude,
                            String longitude) {
}
