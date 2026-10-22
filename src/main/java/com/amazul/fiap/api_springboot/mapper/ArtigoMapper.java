package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.model.Artigo;
import com.amazul.fiap.api_springboot.model.ArtigoFonte;
import com.amazul.fiap.api_springboot.model.Fonte;

import java.util.List;

import static java.util.stream.Collectors.toList;

public final class ArtigoMapper {
    private ArtigoMapper(){

    }
    public static ArtigoResponse toResponse(Artigo artigo){
        return new ArtigoResponse(artigo.getId(), artigo.getAreaAplicacao().name(), artigo.getTitulo(), artigo.getResumo(),
                artigo.getAplicacaoReal(), artigo.getImpactoSociedade(), artigo.getStatus().name(),
                artigo.getReferencias() == null ? List.of() : artigo.getReferencias()
                        .stream()
                        .map(ArtigoFonte::getFonte)
                        .map(Fonte::getUrl)
                        .toList());
    }

    public static List<ArtigoResponse> toResponse(List<Artigo> artigos){
        return artigos.stream().map(ArtigoMapper::toResponse).toList();
    }
}
