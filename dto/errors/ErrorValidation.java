package com.amazul.fiap.api_springboot.dto.errors;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorValidation(String erro,
                              int status,
                              List<FieldError> campos,
                              LocalDateTime timestamp) {
}
