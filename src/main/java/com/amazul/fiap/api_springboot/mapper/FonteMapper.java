package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.FonteResponse;
import com.amazul.fiap.api_springboot.model.Artigo;
import com.amazul.fiap.api_springboot.model.ArtigoFonte;
import com.amazul.fiap.api_springboot.model.Fonte;

import java.util.List;

import static java.util.stream.Collectors.toList;

public final class FonteMapper {
    private FonteMapper(){

    }

    public static FonteResponse toResponse(Fonte fonte){
        return new FonteResponse(fonte.getId(), fonte.getTitulo(), fonte.getUrl(),
                fonte.getArtigos().stream()
                        .map(ArtigoFonte::getArtigo)
                        .map(Artigo::getTitulo)
                        .toList());
    }
    public static List<FonteResponse> toResponse(List<Fonte> fontes){
        return fontes.stream().map(FonteMapper::toResponse).toList();
    }
}
