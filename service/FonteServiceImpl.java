package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.response.FonteResponse;
import com.amazul.fiap.api_springboot.exception.ResourceNotFoundException;
import com.amazul.fiap.api_springboot.model.Fonte;
import com.amazul.fiap.api_springboot.repository.FonteRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FonteServiceImpl implements FonteService{
    private final FonteRepository repository;

    public FonteServiceImpl(FonteRepository repository){
        this.repository = repository;
    }
    @Override
    public Page<FonteResponse> listarTodos(int pagina, int tamanho) {
        return null;
    }

    public Fonte findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fonte", id));
    }
}
