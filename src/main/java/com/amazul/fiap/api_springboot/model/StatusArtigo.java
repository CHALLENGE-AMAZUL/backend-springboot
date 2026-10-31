package com.amazul.fiap.api_springboot.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status editorial do artigo no ciclo de vida da plataforma")
public enum StatusArtigo {
    @Schema(description = "Artigo em elaboração inicial (status padrão no momento da criação)")
    RASCUNHO,

    @Schema(description = "Artigo revisado e publicado para visualização pública")
    PUBLICADO,

    @Schema(description = "Artigo descontinuado ou mantido apenas para histórico documental")
    ARQUIVADO
}
