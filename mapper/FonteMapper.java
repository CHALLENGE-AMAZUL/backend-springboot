package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.FonteResponse;
import com.amazul.fiap.api_springboot.model.Fonte;

public final class FonteMapper {
    private FonteMapper(){

    }

    public static FonteResponse toResponse(Fonte fonte){
        return new();
    }
}
