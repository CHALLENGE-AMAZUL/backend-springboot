package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.service.ArtigoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artigos")
@Tag(name = "Artigo", description = "Endpoints de artigos de tecnologia nuclear.")
public class ArtigoController {
    private final ArtigoService artigoService;

    public ArtigoController(ArtigoService artigoService){
        this.artigoService = artigoService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os artigos com paginação")
    public PageResponse<ArtigoResponse> listar(@RequestParam(required = false, defaultValue = "0") Integer pagina,
                                               @RequestParam(required = false, defaultValue = "10") Integer tamanho){
        return PageResponse.from(artigoService.listarTodos(pagina, tamanho));
    }
}
