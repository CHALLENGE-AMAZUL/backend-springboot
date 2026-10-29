package com.amazul.fiap.api_springboot.mapper;

import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.model.Artigo;

import java.util.List;

public final class ArtigoMapper {
    private ArtigoMapper(){

    }
    public static ArtigoResponse toResponse(Artigo artigo){
        String nomeLocal = artigo.getLocal() != null ? artigo.getLocal().getNome() : null;
        String pais = artigo.getLocal() != null ? artigo.getLocal().getPais() : null;
        String status = artigo.getStatus() != null ? artigo.getStatus().name() : null;
        String area = artigo.getAreaAplicacao() != null ? artigo.getAreaAplicacao().name() : null;

        List<String> referencias = artigo.getReferencias() == null ? List.of() : artigo.getReferencias()
                .stream()
                .filter(ref -> ref.getFonte() != null)
                .map(ref -> ref.getFonte().getUrl())
                .filter(url -> url != null && !url.isBlank())
                .toList();

        return new ArtigoResponse(
                artigo.getId(),
                area,
                artigo.getTitulo(),
                artigo.getResumo(),
                artigo.getAplicacaoReal(),
                artigo.getImpactoSociedade(),
                nomeLocal,
                pais,
                status,
                referencias,
                artigo.getAtualizadoEm()
        );
    }

    public static List<ArtigoResponse> toResponse(List<Artigo> artigos){
        return artigos.stream().map(ArtigoMapper::toResponse).toList();
    }
}
