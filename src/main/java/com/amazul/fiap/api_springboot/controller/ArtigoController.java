package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.errors.ErrorResponse;
import com.amazul.fiap.api_springboot.dto.request.ArtigoRequest;
import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.service.ArtigoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/artigos")
@Tag(name = "Artigo", description = "Endpoints de artigos de tecnologia nuclear.")
public class ArtigoController {
    private final ArtigoService artigoService;

    public ArtigoController(ArtigoService artigoService){
        this.artigoService = artigoService;
    }

    @GetMapping
    @Operation(
            summary = "Lista todos os artigos com paginação",
            description = "Recupera uma página de artigos cadastrados no acervo educativo da AMAZUL, incluindo dados do local e referências bibliográficas."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Página de artigos retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros de paginação inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public PageResponse<ArtigoResponse> listar(@RequestParam(required = false, defaultValue = "0") Integer pagina,
                                               @RequestParam(required = false, defaultValue = "10") Integer tamanho){
        return PageResponse.from(artigoService.listarTodos(pagina, tamanho));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista único artigo com parâmetro id")
    public ResponseEntity<ArtigoResponse> listarUnico(@PathVariable @Validated @Positive Long id){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{titulo}")
    @Operation(summary = "Lista todos os artigos que contenham o título inserido")
    public PageResponse<ArtigoResponse> exibirPorTitulo(@RequestParam(required = true) String titulo,
                                                        @RequestParam(required = false, defaultValue = "0") Integer pagina,
                                                        @RequestParam(required = false, defaultValue = "10") Integer tamanho){
        return PageResponse.from(artigoService.buscarPorTitulo(titulo, pagina, tamanho));
    }

    @PostMapping
    @Operation(summary = "Criar novo artigo")
    public ResponseEntity<ArtigoResponse> criar(@RequestBody @Valid ArtigoRequest request){
        ArtigoResponse artigo = artigoService.criar(request);
        return ResponseEntity.created(URI.create("/api/artigos")).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar artigo existente")
    public ResponseEntity<ArtigoResponse> atualizar(@PathVariable @Validated @Positive Long id, @RequestBody @Valid ArtigoRequest request){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar artigo existente",
            description = "Remove definitivamente um artigo cadastrado através de seu identificador numérico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Artigo excluído com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Identificador de artigo inválido (não positivo ou formato incorreto)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Artigo não encontrado para o ID informado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<Void> apagar(@PathVariable @Validated @Positive Long id){
        artigoService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
