package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.request.FonteRequest;
import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.dto.response.FonteResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.service.FonteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @Operation(summary = "Lista única fonte com parâmetro id")
    public ResponseEntity<FonteResponse> listarUnica(@Validated @PathVariable Long id){
    }

    @PostMapping
    @Operation(summary = "Criação de fonte de artigo")
    public ResponseEntity<FonteResponse> criar(){

    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtigoResponse> atualizar(@Validated @PathVariable Long id, @RequestBody @Valid FonteRequest request){

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@Validated @PathVariable Long id){

    }

}
