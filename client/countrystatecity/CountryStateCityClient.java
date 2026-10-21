package com.amazul.fiap.api_springboot.client.countrystatecity;

import com.amazul.fiap.api_springboot.config.RestClientConfig;
import com.amazul.fiap.api_springboot.dto.response.PaisCodigoResponse;
import com.amazul.fiap.api_springboot.dto.response.PaisDetalhesResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class CountryStateCityClient {
    private final RestClient restClient;

    public CountryStateCityClient(RestClient countryStateRestClient) {
        this.restClient = countryStateRestClient;
    }

    public List<PaisCodigoResponse> listarPaises(){
        return restClient.get()
                .uri("https://api.countrystatecity.in/v1/countries")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public PaisDetalhesResponse buscarPais(String codigoPais){
        return restClient.get()
                .uri("https://api.countrystatecity.in/v1/countries/{codigoPais}", codigoPais.toUpperCase())
                .retrieve()
                .body(PaisDetalhesResponse.class);
    }
}
