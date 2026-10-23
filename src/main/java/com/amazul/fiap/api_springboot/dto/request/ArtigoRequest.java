package com.amazul.fiap.api_springboot.dto.request;

import com.amazul.fiap.api_springboot.model.AreaAplicacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Inserção de campos para criação de artigo")
public record ArtigoRequest(
        @Schema(description = "Título principal do Artigo",
                example = "Produção de Radiofármacos para Diagnóstico e Tratamento Oncológico",
                maxLength = 150,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Título deve ser inserido!")
        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres")
        String titulo,

        @Schema(description = "Resumo do conceito apresentado",
                example = "Visão geral sobre o papel dos reatores de pesquisa na geração de radioisótopos essenciais para a medicina moderna.",
                maxLength = 500,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Resumo deve ser inserido!")
        @Size(max = 500, message = "O resumo deve ter até 500 caracteres")
        String resumo,

        @Schema(description = "Exemplo prático e científico da tecnologia nuclear em operação",
                example = "Uso do radioisótopo Tecnécio-99m (Tc-99m) produzido pelo IPEN/CNEN em procedimentos de cintilografia para mapeamento de órgãos vitais.",
                maxLength = 150,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Aplicação real deve ser detalhada!")
        String aplicacaoReal,

        @Schema(description = "Impactos diretos, benefícios e contribuição para a população e sociedade civil",
                example = "Possibilita diagnósticos precoces de tumores e doenças cardíacas no SUS, beneficiando milhões de pacientes anualmente com tratamentos não invasivos.",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Impacto na sociedade deve ser preenchido!")
        String impactoSociedade,

        @Schema(description = "Área/Setor de aplicação da tecnologia nuclear",
                example = "SAUDE",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Área de aplicação deve ser inserida")
        AreaAplicacao area,

        LocalRequest local
        //List<ArtigoFonte> referencias;
){
}
