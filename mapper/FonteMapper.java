package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.FonteResponse;
import com.amazul.fiap.api_springboot.model.Fonte;

import java.util.List;

public final class FonteMapper {
    private FonteMapper(){

    }

    public static FonteResponse toResponse(Fonte fonte){
        return new();
    }
    public static List<FonteResponse> toResponse(Fonte fonte){
        return new();
    }
}
