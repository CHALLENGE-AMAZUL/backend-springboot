package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.errors.ErrorResponse;
import com.amazul.fiap.api_springboot.dto.request.ArtigoRequest;
import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.mapper.ArtigoMapper;
import com.amazul.fiap.api_springboot.model.StatusArtigo;
import com.amazul.fiap.api_springboot.service.ArtigoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.websocket.server.PathParam;
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
                    content = @Content(schema = @Schema(implementation = PageResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros de paginação inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public PageResponse<ArtigoResponse> listar(@RequestParam(required = false, defaultValue = "0") Integer pagina,
                                               @RequestParam(required = false, defaultValue = "10") Integer tamanho){
        return PageResponse.from(artigoService.listarTodos(pagina, tamanho));
    }

    @GetMapping("/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Artigo de id (xx) retornado com sucesso",
            content = @Content(schema = @Schema(implementation = PageResponse.class))
    )
    @Operation(summary = "Lista único artigo com parâmetro id")
    public ResponseEntity<ArtigoResponse> listarUnico(@PathVariable @Validated @Positive Long id){
        return ResponseEntity.ok(ArtigoMapper.toResponse(artigoService.findById(id)));
    }

    @GetMapping("/busca")
    @Operation(summary = "Lista todos os artigos que contenham o título inserido")
    @ApiResponse(
            responseCode = "200",
            description = "Artigos contendo o texto inserido listados",
            content = @Content(schema = @Schema(implementation = PageResponse.class))
    )
    public PageResponse<ArtigoResponse> exibirPorTitulo(@RequestParam() String titulo,
                                                        @RequestParam(required = false, defaultValue = "0") Integer pagina,
                                                        @RequestParam(required = false, defaultValue = "10") Integer tamanho){
        return PageResponse.from(artigoService.buscarPorTitulo(titulo, pagina, tamanho));
    }

    @PostMapping
    @Operation(summary = "Criar novo artigo")
    @ApiResponse(
            responseCode = "201",
            description = "Artigo criado com sucesso",
            content = @Content(schema = @Schema(implementation = PageResponse.class))
    )
    public ResponseEntity<ArtigoResponse> criar(@RequestBody @Valid ArtigoRequest request){
        ArtigoResponse artigo = artigoService.criar(request);
        return ResponseEntity.created(URI.create("/api/artigos/" + artigo.id())).body(artigo);
    }


    //Pensando nesses 3 métodos
//    @PostMapping("/{id}/fontes/{fonteId}")
//    @Operation(summary = "Adicionar fonte a lista de referências - talvez via Url")
//    public ResponseEntity<ArtigoResponse> associarFonte(@PathVariable @Validated @Positive Long id, @PathVariable @Validated @Positive Long fonteId){
//        return ResponseEntity.ok(artigoService.novoMetodo(id, fonteId));
//    }
//    @DeleteMapping("/{id}/fontes/{url}")
//    @Operation(summary = "Remover fonte da lista de referencias - talvez via Url")
//    public ResponseEntity<ArtigoResponse> associarFonte(@PathVariable @Validated @Positive Long id, @PathVariable @Validated @Positive Long fonteId){
//        return ResponseEntity.ok(artigoService.novoMetodo(id, fonteId));
//    }
//
//    @PostMapping("/{id}/locais/{}")
//    @Operation(summary = "Inserir ou atualizar local do artigo")
//    public ResponseEntity<ArtigoResponse> adicionarOuAtualizarLocal(@PathVariable @Validated @Positive Long id){
//        return ResponseEntity.ok(artigoService.novoMetodo(id));
//    }

    @PutMapping("/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Artigo atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = PageResponse.class))
    )
    @Operation(summary = "Atualizar artigo existente")
    public ResponseEntity<ArtigoResponse> atualizar(@PathVariable @Validated @Positive Long id, @RequestBody @Valid ArtigoRequest request){
        return ResponseEntity.ok(artigoService.atualizar(id, request));
    }

    @PutMapping("/alterarStatus/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Status de artigo atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = PageResponse.class))
    )
    @Operation(summary = "Alterar status de artigo")
    public ResponseEntity<ArtigoResponse> alterarStatus(@PathVariable @Validated @Positive Long id, @RequestParam @Valid StatusArtigo status){
        return ResponseEntity.ok(artigoService.mudarStatus(id, status));
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
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Artigo não encontrado para o ID informado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<Void> apagar(@PathVariable @Validated @Positive Long id){
        artigoService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
