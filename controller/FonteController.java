package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.dto.response.FonteResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.service.FonteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fontes")
@Tag(name = "Fonte", description = "Endpoints das fontes dos artigos de tecnologia nuclear.")
public class FonteController {
    private final FonteService fonteService;

    public FonteController( FonteService fonteService){
        this.fonteService = fonteService;
    }

    @GetMapping
    @Operation(summary = "Lista todas as fontes com paginação")
    public PageResponse<FonteResponse> listar(@RequestParam(required = false, defaultValue = "0") Integer pagina,
                                              @RequestParam(required = false, defaultValue = "10") Integer tamanho){
        return PageResponse.from(fonteService.listarTodos(pagina, tamanho));
    }
}
