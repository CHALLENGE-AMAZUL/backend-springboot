package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.errors.ErrorResponse;
import com.amazul.fiap.api_springboot.dto.errors.ErrorValidation;
import com.amazul.fiap.api_springboot.dto.request.FonteRequest;
import com.amazul.fiap.api_springboot.dto.response.FonteResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.mapper.FonteMapper;
import com.amazul.fiap.api_springboot.service.FonteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/fontes")
@Tag(name = "Fonte", description = "Endpoints das fontes e referências bibliográficas científicas.")
public class FonteController {
    private final FonteService fonteService;

    public FonteController( FonteService fonteService){
        this.fonteService = fonteService;
    }

    @GetMapping
    @Operation(
            summary = "Lista todas as fontes com paginação",
            description = "Retorna uma página de fontes de referência cadastradas, indicando os títulos dos artigos vinculados a cada uma."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Página de fontes recuperada com sucesso",
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
    public PageResponse<FonteResponse> listar(
            @Parameter(description = "Número da página (base 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer pagina,
            @Parameter(description = "Quantidade de registros por página", example = "10")
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer tamanho){
        return PageResponse.from(fonteService.listarTodos(pagina, tamanho));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Lista fonte única com parâmetro id",
            description = "Recupera uma fonte bibliográfica e científica específica a partir de seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fonte encontrada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FonteResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Identificador de fonte inválido ou não positivo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Fonte não encontrada para o ID informado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<FonteResponse> listarUnica(
            @Parameter(description = "Identificador numérico positivo da fonte", example = "1", required = true)
            @PathVariable @Validated @Positive Long id){
        return ResponseEntity.ok(FonteMapper.toResponse(fonteService.findById(id)));
    }

    @SecurityRequirement(name = "basicAuth")
    @PostMapping
    @Operation(
            summary = "Criação de fonte de artigo",
            description = "Registra uma nova fonte de informação ou referência oficial de um artigo."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Fonte criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FonteResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos no corpo da requisição",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorValidation.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<FonteResponse> criar(@RequestBody @Valid FonteRequest request){
        FonteResponse fonte = fonteService.criar(request);
        return ResponseEntity.created(URI.create("/api/fontes/" + fonte.id())).body(fonte);
    }

    @SecurityRequirement(name = "basicAuth")
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar fonte de artigo existente",
            description = "Atualiza os dados de título e URL de uma referência científica existente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fonte atualizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FonteResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de requisição inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorValidation.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Fonte não encontrada para o ID informado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<FonteResponse> atualizar(
            @Parameter(description = "Identificador numérico positivo da fonte", example = "1", required = true)
            @PathVariable @Validated @Positive Long id,
            @RequestBody @Valid FonteRequest request){
        return ResponseEntity.ok(fonteService.atualizar(id, request));
    }

    @SecurityRequirement(name = "basicAuth")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar uma fonte existente",
            description = "Exclui definitivamente uma fonte de referência científica do sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fonte deletada com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID informado é inválido ou menor/igual a zero",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Fonte não encontrada para o ID informado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<Void> apagar(
            @Parameter(
                    description = "Identificador numérico positivo da fonte",
                    example = "1",
                    required = true
            )
            @PathVariable @Validated @Positive Long id){
        fonteService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
