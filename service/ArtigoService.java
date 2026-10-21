package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.request.ArtigoRequest;
import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.model.Artigo;
import org.springframework.data.domain.Page;

public interface ArtigoService {
    Artigo findById(Long id);
    Page<ArtigoResponse> listarTodos(Integer pagina, Integer tamanho);
    Page<ArtigoResponse> buscarPorTitulo(String titulo, Integer pagina, Integer tamanho);
    ArtigoResponse criar(ArtigoRequest request);
    ArtigoResponse atualizar(Long id, ArtigoRequest request);
    void apagar(Long id);
}
