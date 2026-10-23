package com.amazul.fiap.api_springboot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Schema(description = "DTO de resposta analítica do artigo")
public record ArtigoResponse(
        Long id,

        @Schema(description = "Área de aplicação da tecnologia nuclear", example = "SAUDE", allowableValues = {"ENERGIA", "SAUDE", "AGRICULTURA", "INDUSTRIA", "PESQUISA"})
        String areaAplicacao,

        String titulo,
        String resumo,

        String aplicacaoReal,
        String impactoSociedade,

        String nomeLocal, //Pode ser nulo
        String paisLocal, //Pode ser nulo

        String status,

        List<String>referencias,
        Date atualizadoEm
) { }
