package com.amazul.fiap.api_springboot.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(List<T> conteudo,
                           int pagina,
                           int tamanho,
                           long totalElementos,
                           int totalPaginas,
                           boolean ultimo) {

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
