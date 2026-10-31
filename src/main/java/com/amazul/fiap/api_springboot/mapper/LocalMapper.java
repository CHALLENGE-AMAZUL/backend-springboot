package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.LocalResponse;
import com.amazul.fiap.api_springboot.model.Local;
import com.amazul.fiap.api_springboot.model.Pais;

import java.util.List;

public final class LocalMapper {
    private LocalMapper(){

    }

    public static LocalResponse toResponse(Local local){
        Pais pais = local.getPais();
        return new LocalResponse(
                local.getId(),
                local.getNome(),
                pais != null ? pais.getNome() : null,
                pais != null ? pais.getCodigoPais() : null,
                pais != null ? pais.getLatitude() : null,
                pais != null ? pais.getLongitude() : null);
    }

    public static List<LocalResponse> toResponse(List<Local> locais){
        return locais.stream().map(LocalMapper::toResponse).toList();
    }
}
