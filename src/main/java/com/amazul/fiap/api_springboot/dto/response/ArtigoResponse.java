package com.amazul.fiap.api_springboot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

@Schema(description = "DTO de resposta detalhada do artigo educativo sobre tecnologia nuclear")
public record ArtigoResponse(
        @Schema(description = "Identificador único do artigo", example = "1")
        Long id,

        @Schema(description = "Área de aplicação da tecnologia nuclear", example = "SAUDE", allowableValues = {"ENERGIA", "SAUDE", "AGRICULTURA", "INDUSTRIA", "PESQUISA"})
        String areaAplicacao,

        @Schema(description = "Título principal do artigo", example = "Produção de Radiofármacos para Diagnóstico e Tratamento Oncológico")
        String titulo,

        @Schema(description = "Resumo executivo do conteúdo", example = "Visão geral sobre o papel dos reatores de pesquisa na geração de radioisótopos essenciais para a medicina moderna.")
        String resumo,

        @Schema(description = "Exemplo prático e tangível da aplicação no cotidiano", example = "Uso do radioisótopo Tecnécio-99m (Tc-99m) produzido pelo IPEN/CNEN em procedimentos de cintilografia para mapeamento de órgãos vitais.")
        String aplicacaoReal,

        @Schema(description = "Benefícios para a sociedade civil e mitigação de mitos", example = "Possibilita diagnósticos precoces de tumores e doenças cardíacas no SUS, beneficiando milhões de pacientes anualmente com tratamentos não invasivos.")
        String impactoSociedade,

        @Schema(description = "Nome da instalação nuclear ou local de aplicação associado", example = "Complexo radiológico de Iperó/Aramar (CTMSP)", nullable = true)
        String nomeLocal,

        @Schema(description = "Nome por extenso do país do local", example = "Brazil", nullable = true)
        String paisLocal,

        @Schema(description = "Status editorial do artigo no ciclo de vida", example = "RASCUNHO", allowableValues = {"RASCUNHO", "PUBLICADO", "ARQUIVADO"})
        String status,

        @Schema(description = "Lista de URLs de fontes científicas que embasam o artigo", example = "[\"https://www.ipen.br/portal_por/portal/interna.php?secao_id=40\"]")
        List<String> referencias,

        @Schema(description = "Data e hora da última atualização do registro", example = "2026-10-26T22:00:00.000Z")
        Date atualizadoEm
) { }
