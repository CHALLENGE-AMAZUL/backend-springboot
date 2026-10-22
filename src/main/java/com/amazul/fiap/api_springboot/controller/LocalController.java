package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.errors.ErrorResponse;
import com.amazul.fiap.api_springboot.dto.request.LocalRequest;
import com.amazul.fiap.api_springboot.dto.response.LocalResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.service.LocalService;
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
@RequestMapping("/api/locais")
@Tag(name = "Local", description = "Endpoints dos locais que envolvem as informações sobre tecnologia nuclear.")
public class LocalController {
    private final LocalService localService;

    public LocalController(LocalService localService){
        this.localService = localService;
    }

    @GetMapping
    @Operation(
            summary = "Lista todos os locais com paginação",
            description = "Retorna uma lista paginada de instalações e locais relacionados à tecnologia nuclear com suas coordenadas."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Página de locais retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros de requisição inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
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
        LocalResponse local = localService.criar(request);
        return ResponseEntity.created(URI.create("/api/locais/" + local.id())).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar local existente")
    public ResponseEntity<LocalResponse> atualizar(@PathVariable @Validated @Positive Long id, @RequestBody @Valid LocalRequest request){
        return ResponseEntity.ok(localService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar local existente",
            description = "Remove definitivamente um ponto geográfico ou instalação da base de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Local excluído com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do local inválido ou não positivo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Local não encontrado para o ID informado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<Void> apagar(@PathVariable @Validated @Positive Long id){
        localService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
