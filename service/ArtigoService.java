package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.model.Artigo;
import com.amazul.fiap.api_springboot.model.Local;
import org.springframework.data.domain.Page;

public interface ArtigoService {
    Artigo findById(Long id);
    Page<ArtigoResponse> listarTodos(int pagina, int tamanho);

    void apagar(Long id);
}
