package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.request.LocalRequest;
import com.amazul.fiap.api_springboot.dto.response.LocalResponse;
import com.amazul.fiap.api_springboot.dto.response.ApiPaisResponse;
import com.amazul.fiap.api_springboot.dto.response.PaisResponse;
import com.amazul.fiap.api_springboot.exception.ResourceNotFoundException;
import com.amazul.fiap.api_springboot.mapper.LocalMapper;
import com.amazul.fiap.api_springboot.model.Local;
import com.amazul.fiap.api_springboot.model.Pais;
import com.amazul.fiap.api_springboot.repository.LocalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@org.springframework.transaction.annotation.Transactional
@Service
public class LocalServiceImpl implements LocalService{
    private final LocalRepository repository;
    private final PaisService paisService;

    public LocalServiceImpl(LocalRepository repository, PaisService paisService){
        this.repository = repository;
        this.paisService = paisService;
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
        local.setNome(request.nome());

        String codigoIso2 = request.codigoPais();

        if(codigoIso2 != null && !codigoIso2.isBlank()){
            Pais pais = paisService.findByCodigoPais(codigoIso2);

            if(pais == null) {
                pais = paisService.criar(codigoIso2);
            }

               local.setPais(pais);
            }

        repository.save(local);
        return LocalMapper.toResponse(local);
    }

    @Override
    public LocalResponse atualizar(Long id, LocalRequest request) {
        Local local = findById(id);

        if(request.nome() != null && !request.nome().isBlank()){
            local.setNome(request.nome());
        }
        if(request.codigoPais() != null && !request.codigoPais().isBlank()){
            Pais pais = paisService.findByCodigoPais(request.codigoPais());

            if(pais == null){
                pais = paisService.criar(request.codigoPais());
            }
            local.setPais(pais);
        }

        repository.save(local);
        return LocalMapper.toResponse(local);
    }

    @Override
    public void apagar(Long id) {
        Local local = findById(id);
        repository.delete(local);
    }
}
