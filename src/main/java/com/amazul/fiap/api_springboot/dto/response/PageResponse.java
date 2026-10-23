package com.amazul.fiap.api_springboot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "Resposta envelopada de paginação de artigos",
        requiredMode = Schema.RequiredMode.REQUIRED)
public record PageResponse<T>(
        @Schema(description = "Lista de elementos da página atual")
        List<T> conteudo,

        @Schema(description = "Índice da página atual (iniciando em 0)", example = "0")
        int pagina,

        @Schema(description = "Quantidade de registros por página", example = "10")
        int tamanho,

        @Schema(description = "Total de registros encontrados na base", example = "42")
        long totalElementos,

        @Schema(description = "Total de páginas disponíveis", example = "5")
        int totalPaginas,

        @Schema(description = "Indica se esta é a última página", example = "false")
        boolean ultimo
        ) {

    public static <T> PageResponse <T> from(Page<T> pagina) {
        return new PageResponse<>(
                pagina.getContent(),
                pagina.getNumber(),
                pagina.getSize(),
                pagina.getTotalElements(),
                pagina.getTotalPages(),
                pagina.isLast()
        );
    }
}
