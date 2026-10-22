package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.request.ArtigoRequest;
import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.exception.ResourceNotFoundException;
import com.amazul.fiap.api_springboot.mapper.ArtigoMapper;
import com.amazul.fiap.api_springboot.model.Artigo;
import com.amazul.fiap.api_springboot.model.StatusArtigo;
import com.amazul.fiap.api_springboot.repository.ArtigoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArtigoServiceImpl implements ArtigoService{
    private final ArtigoRepository repository;

    public ArtigoServiceImpl(ArtigoRepository repository){
        this.repository = repository;
    }
    @Override
    public Page<ArtigoResponse> listarTodos(Integer pagina, Integer tamanho) {
        Pageable pageable = Pageable.ofSize(tamanho).withPage(pagina);
        return repository.findAll(pageable).map(ArtigoMapper::toResponse);
    }

    @Override
    public Artigo findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artigo", id));
    }

    @Override
    public Page<ArtigoResponse> buscarPorTitulo(String titulo, Integer pagina, Integer tamanho) {
        Pageable pageable = Pageable.ofSize(tamanho).withPage(pagina);
        return repository.findByTituloContainingIgnoreCase(titulo, pageable).map(ArtigoMapper::toResponse);
    }

    public ArtigoResponse criar(ArtigoRequest request){
        Artigo artigo = new Artigo();
        artigo.setId(null);
        artigo.setTitulo(request.titulo());
        artigo.setResumo(request.resumo());
        artigo.setAplicacaoReal(request.aplicacaoReal());
        artigo.setImpactoSociedade(request.impactoSociedade());
        artigo.setAreaAplicacao(request.area());

        repository.save(artigo);
        return ArtigoMapper.toResponse(artigo);
    }

    @Override
    public ArtigoResponse atualizar(Long id, ArtigoRequest request) {
        Artigo artigo = findById(id);

//        if(artigo.getStatus() == StatusArtigo.PUBLICADO)

//        if (existing.getStatus() == MilestoneStatus.CONCLUIDA) {
//            throw new MilestoneAlreadyCompletedException(id);
//        }
        return ArtigoMapper.toResponse(artigo);
    }

    @Override
    public void apagar(Long id) {
        Artigo artigo = findById(id);
        repository.delete(artigo);
    }
}
