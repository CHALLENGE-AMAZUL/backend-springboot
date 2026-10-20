package com.amazul.fiap.api_springboot.dto.response;

public record LocalResponse(Long id,
                            String nome,
                            String cidade,
                            String codigoPais,
                            Double latitude,
                            Double longitude) {
}
