package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.FonteResponse;
import com.amazul.fiap.api_springboot.model.Fonte;

import java.util.List;

public final class FonteMapper {
    private FonteMapper(){

    }

    public static FonteResponse toResponse(Fonte fonte){
        return new FonteResponse(fonte.getId(), fonte.getTitulo(), fonte.getUrl(), fonte.getDataConsulta());
    }
    public static List<FonteResponse> toResponse(List<Fonte> fontes){
        return fontes.stream().map(FonteMapper::toResponse).toList();
    }
}
