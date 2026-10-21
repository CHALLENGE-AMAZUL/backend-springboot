package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.request.LocalRequest;
import com.amazul.fiap.api_springboot.dto.response.LocalResponse;
import com.amazul.fiap.api_springboot.exception.ResourceNotFoundException;
import com.amazul.fiap.api_springboot.mapper.LocalMapper;
import com.amazul.fiap.api_springboot.model.Local;
import com.amazul.fiap.api_springboot.repository.LocalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocalServiceImpl implements LocalService{
    private final LocalRepository repository;

    public LocalServiceImpl(LocalRepository repository){
        this.repository = repository;
    }

    @Override
    public Page<LocalResponse> listarTodos(int pagina, int tamanho) {
        Pageable pageable = Pageable.ofSize(tamanho).withPage(pagina);
        return repository.findAll(pageable).map(LocalMapper::toResponse);
    }

    public Local findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Local", id));
    }

    @Override
    public LocalResponse criar(LocalRequest request) {
        Local local = new Local();
        local.setId(null);
        return LocalMapper.toResponse(local);
    }

    @Override
    public LocalResponse atualizar(Long id, LocalRequest request) {
        return null;
    }

    @Override
    public void apagar(Long id) {
        Local local = findById(id);
        repository.delete(local);
    }
}
