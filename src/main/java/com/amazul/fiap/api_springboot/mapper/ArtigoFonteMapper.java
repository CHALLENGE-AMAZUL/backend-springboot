package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.ArtigoFonteResponse;
import com.amazul.fiap.api_springboot.model.ArtigoFonte;

public final class ArtigoFonteMapper {
    private ArtigoFonteMapper() {
    }
    public ArtigoFonteResponse toResponse(ArtigoFonte artigoFonte){
        return new ArtigoFonteResponse(artigoFonte.getId(), artigoFonte.getArtigo().getTitulo(),
                                    artigoFonte.getFonte().getUrl(), artigoFonte.getObservacao());
    }
}
