package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.LocalResponse;
import com.amazul.fiap.api_springboot.model.Local;

import java.util.List;

public final class LocalMapper {
    private LocalMapper(){

    }

    public static LocalResponse toResponse(Local local){
        return new LocalResponse(local.getId(),
                local.getNome(),
                local.getPais().getNome(),
                local.getPais().getCodigoPais(),
                local.getPais().getLatitude(),
                local.getPais().getLongitude());
    }

    public static List<LocalResponse> toResponse(List<Local> locais){
        return locais.stream().map(LocalMapper::toResponse).toList();
    }
}
