package com.amazul.fiap.api_springboot.dto.response;

import java.time.LocalDate;
import java.util.List;

public record FonteResponse(Long id,
                            String titulo,
                            String url,
                            List<String> artigos
                            //,LocalDate dataConsulta
) {
}
