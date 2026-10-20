package com.amazul.fiap.api_springboot.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FonteResponse(Long id,
                            String titulo,
                            String url,
                            LocalDate dataConsulta) {
}
