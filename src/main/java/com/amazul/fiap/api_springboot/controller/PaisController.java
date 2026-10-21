package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.response.PaisCodigoResponse;
import com.amazul.fiap.api_springboot.dto.response.PaisDetalhesResponse;
import com.amazul.fiap.api_springboot.service.PaisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/paises")
@Tag(name = "Paises", description = "Endpoints de integração com a API Country State City, para informação de geolocalização.")
public class PaisController {
    private final PaisService paisService;

    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista com todos os países e seus Códigos", description = "Exemplo: BR - Brasil")
    public List<String> listarPaises(){
        return paisService.listarTodos();
    }


    @GetMapping("/{codigoPais}")
    @Operation(summary = "Retorna informações do país inserido", description = "Retorna nome, código, latitude e longitude")
    public PaisDetalhesResponse exibePais(@PathVariable @NotBlank String codigoPais){
        return paisService.exibirPais(codigoPais);
    }
}
