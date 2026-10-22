package com.amazul.fiap.api_springboot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "DTO de resposta representando uma fonte bibliográfica e científica")
public record FonteResponse(Long id,
                            String titulo,
                            String url,
                            List<String> artigos,
                            LocalDate atualizadoEm
                            //,LocalDate dataConsulta
) {
}
