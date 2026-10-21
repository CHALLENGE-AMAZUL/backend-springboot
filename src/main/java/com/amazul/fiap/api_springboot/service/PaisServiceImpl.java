package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.client.countrystatecity.CountryStateCityClient;
import com.amazul.fiap.api_springboot.dto.response.PaisCodigoResponse;
import com.amazul.fiap.api_springboot.dto.response.PaisDetalhesResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaisServiceImpl implements PaisService{
    private final CountryStateCityClient countryStateCityClient;

    public PaisServiceImpl(CountryStateCityClient countryStateCityClient) {
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
    public PaisDetalhesResponse exibirPais(String codigoPais) {
        return countryStateCityClient.buscarPais(codigoPais);
    }
}
