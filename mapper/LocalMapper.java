package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.LocalResponse;
import com.amazul.fiap.api_springboot.model.Local;

import java.util.List;

public final class LocalMapper {
    private LocalMapper(){

    }

    public static LocalResponse toResponse(Local local){
        return new LocalResponse(local.getId(), local.getNome(), local.getCidade(), local.getNomePais(), local.getCodigoPais(),
                    local.getLatitude(), local.getLongitude());
    }

    public static List<LocalResponse> toResponse(List<Local> locais){
        return locais.stream().map(LocalMapper::toResponse).toList();
    }
}
