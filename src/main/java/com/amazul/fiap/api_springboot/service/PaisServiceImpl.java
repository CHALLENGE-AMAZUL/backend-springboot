package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.client.countrystatecity.CountryStateCityClient;
import com.amazul.fiap.api_springboot.dto.response.PaisCodigoResponse;
import com.amazul.fiap.api_springboot.dto.response.ApiPaisResponse;
import com.amazul.fiap.api_springboot.exception.ResourceNotFoundException;
import com.amazul.fiap.api_springboot.model.Pais;
import com.amazul.fiap.api_springboot.repository.PaisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaisServiceImpl implements PaisService{
    private final PaisRepository repository;
    private final CountryStateCityClient countryStateCityClient;

    public PaisServiceImpl(PaisRepository repository, CountryStateCityClient  countryStateCityClient) {
        this.repository = repository;
        this.countryStateCityClient = countryStateCityClient;
    }

    @Override
    public List<String> listarTodos() {
        List<PaisCodigoResponse> paises = countryStateCityClient.listarPaises();

        return paises.stream()
                .map( p -> p.codigoPais() + " - "  + p.nomePais())
                .toList();
    }

    @Override
    public ApiPaisResponse exibirPais(String codigoPais) {
        return countryStateCityClient.buscarPais(codigoPais);
    }

    public Pais findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("País", id));
    }

    public Pais findByCodigoPais(String codigo){
        return repository.findByCodigoPais(codigo);
    }
}
