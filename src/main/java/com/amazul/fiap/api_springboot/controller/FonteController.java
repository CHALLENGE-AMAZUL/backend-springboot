package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.errors.ErrorResponse;
import com.amazul.fiap.api_springboot.dto.request.FonteRequest;
import com.amazul.fiap.api_springboot.dto.response.FonteResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.service.FonteService;
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
@RequestMapping("/api/fontes")
@Tag(name = "Fonte", description = "Endpoints das fontes dos artigos de tecnologia nuclear.")
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
    public PageResponse<FonteResponse> listar(@RequestParam(required = false, defaultValue = "0") Integer pagina,
                                              @RequestParam(required = false, defaultValue = "10") Integer tamanho){
        return PageResponse.from(fonteService.listarTodos(pagina, tamanho));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista única fonte com parâmetro id")
    public ResponseEntity<FonteResponse> listarUnica(@PathVariable @Validated @Positive Long id){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Operation(summary = "Criação de fonte de artigo")
    public ResponseEntity<FonteResponse> criar(@RequestBody @Valid FonteRequest request){
        FonteResponse fonte = fonteService.criar(request);
        return ResponseEntity.created(URI.create("/api/fontes")).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar fonte de artigo existente")
    public ResponseEntity<FonteResponse> atualizar(@PathVariable @Validated @Positive Long id, @RequestBody @Valid FonteRequest request){
        return ResponseEntity.ok().build();
    }

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
    public ResponseEntity<Void> apagar(@PathVariable @Validated @Positive Long id){
        fonteService.apagar(id);
        return ResponseEntity.noContent().build();
    }

}
