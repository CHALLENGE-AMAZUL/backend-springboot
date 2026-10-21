package com.amazul.fiap.api_springboot.dto.response;

import java.util.List;

public record ArtigoResponse(Long id,
                             String areaAplicacao,
                             String resumo,
                             String corpo,
                             String nomeLocal, //pode ser nulo
                             String paisLocal, //pode ser nulo
                             String status,
                             List<String>referencias
) {
}
