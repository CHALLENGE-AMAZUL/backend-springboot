package com.amazul.fiap.api_springboot.dto.errors;

import java.time.LocalDateTime;

public record ErrorResponse(String mensagem,
                            String erro,
                            int status,
                            LocalDateTime timestamp) {
}
