package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.response.FonteResponse;
import com.amazul.fiap.api_springboot.model.Fonte;
import org.springframework.data.domain.Page;

public interface FonteService {
    Fonte findById(Long id);
    Page<FonteResponse> listarTodos(int pagina, int tamanho);
}
