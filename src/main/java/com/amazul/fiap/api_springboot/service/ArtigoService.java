package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.request.ArtigoFonteRequest;
import com.amazul.fiap.api_springboot.dto.request.ArtigoRequest;
import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.model.Artigo;
import com.amazul.fiap.api_springboot.model.StatusArtigo;
import org.springframework.data.domain.Page;

public interface ArtigoService {
    Artigo findById(Long id);
    Page<ArtigoResponse> listarTodos(Integer pagina, Integer tamanho);
    Page<ArtigoResponse> buscarPorTitulo(String titulo, Integer pagina, Integer tamanho);
    Page<ArtigoResponse> buscarPorPais(String pais, Integer pagina, Integer tamanho);
    ArtigoResponse criar(ArtigoRequest request);
    ArtigoResponse atualizar(Long id, ArtigoRequest request);
    ArtigoResponse inserirOuAtualizarLocal(Long artigoId, Long localId);
    ArtigoResponse associarFonteAoArtigo(Long artigoId,  ArtigoFonteRequest request);
    ArtigoResponse desassociarFonte(Long artigoId, Long fonteId);
    ArtigoResponse mudarStatus(Long id, StatusArtigo status);
    void apagar(Long id);
}
