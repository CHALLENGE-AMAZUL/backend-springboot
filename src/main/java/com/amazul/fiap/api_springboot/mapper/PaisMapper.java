package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.PaisResponse;
import com.amazul.fiap.api_springboot.model.Pais;

public final class PaisMapper {
    private PaisMapper() {
    }

    public static PaisResponse toResponse(Pais pais){
        return new PaisResponse(pais.getId(),
                pais.getNome(),
                pais.getCodigoPais(),
                pais.getLatitude(),
                pais.getLongitude());
    }
}
