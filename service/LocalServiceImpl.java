package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.response.LocalResponse;
import com.amazul.fiap.api_springboot.exception.ResourceNotFoundException;
import com.amazul.fiap.api_springboot.model.Local;
import com.amazul.fiap.api_springboot.repository.LocalRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class LocalServiceImpl implements LocalService{
    private final LocalRepository repository;

    public LocalServiceImpl(LocalRepository repository){
        this.repository = repository;
    }

    @Override
    public Page<LocalResponse> listarTodos(int pagina, int tamanho) {
        return null;
    }

    public Local findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Local", id));
    }
}
