package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.request.LocalRequest;
import com.amazul.fiap.api_springboot.dto.response.LocalResponse;
import com.amazul.fiap.api_springboot.model.Local;
import org.springframework.data.domain.Page;

public interface LocalService {
    Local findById(Long id);
    Page<LocalResponse> listarTodos(int pagina, int tamanho);
    LocalResponse criar(LocalRequest request);
    LocalResponse atualizar(Long id, LocalRequest request);
    void apagar(Long id);
}
