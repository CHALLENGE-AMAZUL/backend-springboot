package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.model.Artigo;

import java.util.List;

public final class ArtigoMapper {
    private ArtigoMapper(){

    }
    public static ArtigoResponse toResponse(Artigo artigo){
        return new();
    }

    public static List<ArtigoResponse> toResponse(Artigo artigo){
        return new();
    }
}
