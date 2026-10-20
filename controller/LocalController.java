package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.request.FonteRequest;
import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.dto.response.LocalResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.service.LocalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locais")
@Tag(name = "Local", description = "Endpoints dos locais que envolvem as informações sobre tecnologia nuclear.")
public class LocalController {
    private final LocalService localService;

    public LocalController(LocalService localService){
        this.localService = localService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os locais com paginação")
    public PageResponse<LocalResponse> listar(@RequestParam(required = false, defaultValue = "0") Integer pagina,
                                              @RequestParam(required = false, defaultValue = "10") Integer tamanho){
        return PageResponse.from(localService.listarTodos(pagina, tamanho));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista único local de artigo com parâmetro id")
    public ResponseEntity<LocalResponse> listarUnico(@Validated @PathVariable Long id){
    }

    @PostMapping
    public ResponseEntity<LocalResponse> criar(){

    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtigoResponse> atualizar(@Validated @PathVariable Long id, @RequestBody @Valid FonteRequest request){

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@Validated @PathVariable Long id){

    }
}
