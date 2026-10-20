package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.LocalResponse;

public final class LocalMapper {
    private LocalMapper(){

    }

    public static LocalResponse toResponse(LocalResponse localResponse){
        return new();
    }
}
