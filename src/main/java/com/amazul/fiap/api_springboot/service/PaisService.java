package com.amazul.fiap.api_springboot.service;

import com.amazul.fiap.api_springboot.dto.response.PaisCodigoResponse;
import com.amazul.fiap.api_springboot.dto.response.PaisDetalhesResponse;

import java.util.List;

public interface PaisService {
    List<String> listarTodos();
    PaisDetalhesResponse exibirPais(String codigoPais);
}
