package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.response.ApiPaisResponse;
import com.amazul.fiap.api_springboot.dto.response.PaisResponse;
import com.amazul.fiap.api_springboot.model.Pais;

import java.util.List;

public interface PaisService {
    List<String> listarTodos();
    ApiPaisResponse exibirPais(String codigoPais);
    Pais findById(Long id);
    Pais findByCodigoPais(String codigoPais);
    Pais criar(String codigoPais);
}
