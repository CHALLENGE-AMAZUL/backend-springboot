package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.errors.ErrorResponse;
import com.amazul.fiap.api_springboot.dto.errors.ErrorValidation;
import com.amazul.fiap.api_springboot.dto.request.ArtigoFonteRequest;
import com.amazul.fiap.api_springboot.dto.request.ArtigoRequest;
import com.amazul.fiap.api_springboot.dto.response.ArtigoResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.mapper.ArtigoMapper;
import com.amazul.fiap.api_springboot.model.StatusArtigo;
import com.amazul.fiap.api_springboot.service.ArtigoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(
        name = "Artigo",
        description = "Endpoints de artigos educativos e científicos sobre tecnologia nuclear."
)
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
    public PageResponse<ArtigoResponse> listar(
            @Parameter(description = "Número da página (base 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") Integer pagina,
            @Parameter(description = "Quantidade de artigos por página", example = "10")
            @RequestParam(required = false, defaultValue = "10") Integer tamanho){
        return PageResponse.from(artigoService.listarTodos(pagina, tamanho));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Lista único artigo com parâmetro id",
            description = "Recupera os detalhes completos de um artigo a partir de seu identificador numérico positivo."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Artigo retornado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtigoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Identificador de artigo inválido ou não positivo",
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
    public ResponseEntity<ArtigoResponse> listarUnico(
            @Parameter(description = "Identificador numérico positivo do artigo", example = "1", required = true)
            @PathVariable @Validated @Positive Long id){
        return ResponseEntity.ok(ArtigoMapper.toResponse(artigoService.findById(id)));
    }

    @GetMapping("/busca")
    @Operation(
            summary = "Lista todos os artigos que contenham o título inserido",
            description = "Pesquisa textual case-insensitive no título dos artigos cadastrados com suporte a paginação."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Artigos contendo o termo pesquisado listados com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros de busca ou paginação inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public PageResponse<ArtigoResponse> exibirPorTitulo(
            @Parameter(description = "Termo ou palavra-chave para busca no título", example = "Radiofármacos", required = true)
            @RequestParam() String titulo,
            @Parameter(description = "Número da página (base 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") Integer pagina,
            @Parameter(description = "Quantidade de registros por página", example = "10")
            @RequestParam(required = false, defaultValue = "10") Integer tamanho){
        return PageResponse.from(artigoService.buscarPorTitulo(titulo, pagina, tamanho));
    }

    @PostMapping
    @Operation(
            summary = "Criar novo artigo",
            description = "Cadastra um novo artigo educativo na base de dados com status inicial RASCUNHO."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Artigo criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtigoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos ou ausência de campos obrigatórios",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorValidation.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<ArtigoResponse> criar(@RequestBody @Valid ArtigoRequest request){
        ArtigoResponse artigo = artigoService.criar(request);
        return ResponseEntity.created(URI.create("/api/artigos/" + artigo.id())).body(artigo);
    }

    @PostMapping("/{id}/fontes")
    @Operation(
            summary = "Adicionar fonte à lista de referências do artigo",
            description = "Associa uma fonte científica cadastrada como referência bibliográfica de um artigo."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fonte associada ao artigo com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtigoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de requisição inválidos ou ID da fonte nulo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorValidation.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Artigo ou Fonte não encontrados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<ArtigoResponse> associarFonte(
            @Parameter(description = "Identificador numérico positivo do artigo", example = "1", required = true)
            @PathVariable @Validated @Positive Long id,
            @RequestBody @Valid ArtigoFonteRequest request){
        return ResponseEntity.ok(artigoService.associarFonteAoArtigo(id, request));
    }

    @DeleteMapping("/{id}/fontes/{fonteId}")
    @Operation(
            summary = "Remover fonte da lista de referências do artigo",
            description = "Desassocia uma fonte científica da lista de referências bibliográficas do artigo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Remoção de fonte do artigo concluída com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Identificador de artigo ou fonte inválido",
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
    public ResponseEntity<Void> removerFonteDoArtigo(
            @Parameter(description = "Identificador numérico positivo do artigo", example = "1", required = true)
            @PathVariable @Validated @Positive Long id,
            @Parameter(description = "Identificador numérico positivo da fonte a remover", example = "1", required = true)
            @PathVariable @Validated @Positive Long fonteId){
        artigoService.desassociarFonte(id, fonteId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/locais/{localId}")
    @Operation(
            summary = "Inserir ou atualizar local do artigo",
            description = "Associa uma instalação física ou contexto geográfico já cadastrado a um artigo existente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Local do artigo atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtigoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Identificador de artigo ou local inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Artigo ou Local não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<ArtigoResponse> adicionarOuAtualizarLocal(
            @Parameter(description = "Identificador numérico positivo do artigo", example = "1", required = true)
            @PathVariable @Validated @Positive Long id,
            @Parameter(description = "Identificador numérico positivo do local", example = "1", required = true)
            @PathVariable @Validated @Positive Long localId){
        return ResponseEntity.ok(artigoService.inserirOuAtualizarLocal(id, localId));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar artigo existente",
            description = "Atualiza os campos informados de um artigo existente e renova o carimbo de data da última modificação."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Artigo atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtigoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de requisição inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorValidation.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Artigo ou Local informado não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<ArtigoResponse> atualizar(
            @Parameter(description = "Identificador numérico positivo do artigo", example = "1", required = true)
            @PathVariable @Validated @Positive Long id,
            @RequestBody @Valid ArtigoRequest request){
        return ResponseEntity.ok(artigoService.atualizar(id, request));
    }

    @PutMapping("/alterarStatus/{id}")
    @Operation(
            summary = "Alterar status de artigo",
            description = "Altera o ciclo de vida do artigo (RASCUNHO -> PUBLICADO -> ARQUIVADO). Dispara HTTP 422 caso a transição solicitada seja ilegal."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Status de artigo atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtigoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Status informado inválido ou ID incorreto",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Artigo não encontrado para o ID informado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Transição inválida de status do artigo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<ArtigoResponse> alterarStatus(
            @Parameter(description = "Identificador numérico positivo do artigo", example = "1", required = true)
            @PathVariable @Validated @Positive Long id,
            @Parameter(description = "Novo status desejado para o artigo", example = "PUBLICADO", required = true)
            @RequestParam @Valid StatusArtigo status){
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
    public ResponseEntity<Void> apagar(
            @Parameter(description = "Identificador numérico positivo do artigo", example = "1", required = true)
            @PathVariable @Validated @Positive Long id){
        artigoService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
