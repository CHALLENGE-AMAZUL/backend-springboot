package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.request.FonteRequest;
import com.amazul.fiap.api_springboot.dto.request.LocalRequest;
import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.dto.response.LocalResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.service.LocalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<LocalResponse> listarUnico(@PathVariable @Validated @Positive Long id){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Operation(summary = "Criar novo local de artigo")
    public ResponseEntity<LocalResponse> criar(@RequestBody @Valid LocalRequest request){
        return ResponseEntity.created(URI.create("/api/locais")).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar local existente")
    public ResponseEntity<LocalResponse> atualizar(@PathVariable @Validated @Positive Long id, @RequestBody @Valid LocalRequest request){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar local existente")
    public ResponseEntity<Void> apagar(@PathVariable @Validated @Positive Long id){
        localService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
