package com.amazul.fiap.api_springboot.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Setores de atuação e áreas com benefícios da tecnologia nuclear")
public enum AreaAplicacao {
    @Schema(description = "Geração de usina com fusão nuclear e propulsão naval")
    ENERGIA,

    @Schema(description = "Medicina nuclear, radioterapia e radiofármacos")
    SAUDE,

    @Schema(description = "Irradiação e conservação de alimentos, e controle de pragas (técnica do inseto estéril)")
    AGRICULTURA,

    @Schema(description = "Radiografia industrial, controle de qualidade para soldas")
    INDUSTRIA,

    @Schema(description = "Pesquisa e desenvolvimento em reatores nucleares e aceleração de partículas")
    PESQUISA
}
