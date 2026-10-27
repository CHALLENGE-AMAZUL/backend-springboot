package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.request.ArtigoFonteRequest;
import com.amazul.fiap.api_springboot.dto.request.ArtigoRequest;
import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.exception.ResourceNotFoundException;
import com.amazul.fiap.api_springboot.mapper.ArtigoMapper;
import com.amazul.fiap.api_springboot.model.*;
import com.amazul.fiap.api_springboot.repository.ArtigoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class ArtigoServiceImpl implements ArtigoService{
    private final ArtigoRepository repository;
    private final LocalService localService;
    private final FonteService fonteService;

    public ArtigoServiceImpl(ArtigoRepository repository, LocalService localService, FonteService fonteService){
        this.repository = repository;
        this.localService = localService;
        this.fonteService = fonteService;
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
        if(request.localId() != null){
            artigo.setLocal((localService.findById(request.localId())));
        }

        artigo.setAtualizadoEm(Date.from(Instant.now()));

        repository.save(artigo);
        return ArtigoMapper.toResponse(artigo);
    }

    @Override
    public ArtigoResponse atualizar(Long id, ArtigoRequest request) {
        Artigo artigo = findById(id);

        if(request.titulo() != null && !request.titulo().isBlank()){
            artigo.setTitulo(request.titulo());
        }

        if(request.resumo() != null && !request.resumo().isBlank()){
            artigo.setResumo(request.resumo());
        }

        if(request.aplicacaoReal() != null && !request.aplicacaoReal().isBlank()){
            artigo.setAplicacaoReal(request.aplicacaoReal());
        }

        if(request.impactoSociedade() != null && !request.impactoSociedade().isBlank()){
            artigo.setImpactoSociedade(request.impactoSociedade());
        }

        if(request.localId() != null){
            artigo.setLocal((localService.findById(request.localId())));
        }

        artigo.setAtualizadoEm(Date.from(Instant.now()));

        repository.save(artigo);
        return ArtigoMapper.toResponse(artigo);
    }

    @Override
    public ArtigoResponse inserirOuAtualizarLocal(Long artigoId, Long localId) {
        Local local = localService.findById(localId);
        Artigo artigo = findById(artigoId);
        artigo.setLocal(local);
        artigo.setAtualizadoEm(Date.from(Instant.now()));

        repository.save(artigo);
        return ArtigoMapper.toResponse(artigo);
    }

    @Override
    public ArtigoResponse associarFonteAoArtigo(Long artigoId, ArtigoFonteRequest request) {
        Artigo artigo = findById(artigoId);
        Fonte fonte = fonteService.findById(request.fonteId());

        ArtigoFonte artigoFonte = new ArtigoFonte();

        artigoFonte.setArtigo(artigo);
        artigoFonte.setFonte(fonte);
        artigoFonte.setObservacao(request.observacao());

        artigo.getReferencias().add(artigoFonte);
        artigo.setAtualizadoEm(Date.from(Instant.now()));

        repository.save(artigo);
        return ArtigoMapper.toResponse(artigo);
    }

    @Override
    public ArtigoResponse desassociarFonte(Long artigoId, Long fonteId) {
        Artigo artigo = findById(artigoId);

        artigo.getReferencias().removeIf(referencia ->
                referencia.getFonte() != null &&
                referencia.getFonte().getId().equals(fonteId));
        artigo.setAtualizadoEm(Date.from(Instant.now()));

        repository.save(artigo);
        return ArtigoMapper.toResponse(artigo);
    }

    @Override
    public ArtigoResponse mudarStatus(Long id, StatusArtigo status) {
    //Posso também alterar a lógica para PUBLICADO -> ARQUIVADO
    //                                 ou ARQUIVADO -> PUBLICADO

        Artigo artigo = findById(id);
        if(artigo == null){
            return null;
        }
        artigo.setStatus(status);
        artigo.setAtualizadoEm(Date.from(Instant.now()));

        repository.save(artigo);
        return ArtigoMapper.toResponse(artigo);
    }


    @Override
    public void apagar(Long id) {
        Artigo artigo = findById(id);
        repository.delete(artigo);
    }
}
