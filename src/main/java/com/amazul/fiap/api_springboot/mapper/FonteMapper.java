package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.FonteResponse;
import com.amazul.fiap.api_springboot.model.Fonte;

import java.util.List;

public final class FonteMapper {
    private FonteMapper(){

    }

    public static FonteResponse toResponse(Fonte fonte){
        List<String> artigos = fonte.getArtigos() == null ? List.of() : fonte.getArtigos()
                .stream()
                .filter(r -> r.getFonte() != null)
                .map(r -> r.getFonte().getUrl())
                .filter(url -> url != null && !url.isBlank())
                .toList();

        return new FonteResponse(
                fonte.getId(),
                fonte.getTitulo(),
                fonte.getUrl(),
                artigos,
                fonte.getAtualizadoEm());
    }

    public static List<FonteResponse> toResponse(List<Fonte> fontes){
        return fontes.stream().map(FonteMapper::toResponse).toList();
    }
}
