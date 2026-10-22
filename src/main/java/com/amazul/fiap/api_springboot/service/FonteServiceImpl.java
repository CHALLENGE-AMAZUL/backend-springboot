package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.request.FonteRequest;
import com.amazul.fiap.api_springboot.dto.response.FonteResponse;
import com.amazul.fiap.api_springboot.exception.ResourceNotFoundException;
import com.amazul.fiap.api_springboot.mapper.FonteMapper;
import com.amazul.fiap.api_springboot.model.Fonte;
import com.amazul.fiap.api_springboot.repository.FonteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FonteServiceImpl implements FonteService {
    private final FonteRepository repository;

    public FonteServiceImpl(FonteRepository repository){
        this.repository = repository;
    }

    @Override
    public Page<FonteResponse> listarTodos(int pagina, int tamanho) {
        Pageable pageable = Pageable.ofSize(tamanho).withPage(pagina);
        return repository.findAll(pageable).map(FonteMapper::toResponse);
    }



    @Override
    public Fonte findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fonte", id));
    }

    @Override
    public FonteResponse criar(FonteRequest request) {
        Fonte fonte = new Fonte();
        fonte.setId(null);

        fonte.setTitulo(request.titulo());
        fonte.setUrl(request.url());
        fonte.setAtualizadoEm(LocalDate.now());
        return FonteMapper.toResponse(fonte);
    }

    @Override
    public FonteResponse atualizar(Long id, FonteRequest request) {
        Fonte fonte = findById(id);

        fonte.setAtualizadoEm(LocalDate.now());
        return FonteMapper.toResponse(fonte);
    }

    @Override
    public void apagar(Long id) {
        Fonte fonte = findById(id);
        repository.delete(fonte);
    }
}
