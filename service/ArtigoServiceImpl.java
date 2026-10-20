package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.exception.ResourceNotFoundException;
import com.amazul.fiap.api_springboot.model.Artigo;
import com.amazul.fiap.api_springboot.repository.ArtigoRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ArtigoServiceImpl implements ArtigoService{
    private final ArtigoRepository repository;

    public ArtigoServiceImpl(ArtigoRepository repository){
        this.repository = repository;
    }
    @Override
    public Page<ArtigoResponse> listarTodos(int pagina, int tamanho) {
        return null;
    }

    public Artigo findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artigo", id));
    }
}
