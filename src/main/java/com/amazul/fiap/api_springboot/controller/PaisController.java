package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.errors.ErrorResponse;
import com.amazul.fiap.api_springboot.dto.errors.ErrorValidation;
import com.amazul.fiap.api_springboot.dto.response.PaisDetalhesResponse;
import com.amazul.fiap.api_springboot.service.PaisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/paises")
@Tag(name = "Paises", description = "Endpoints de integração com a API Country State City, para informação de geolocalização.")
public class PaisController {
    private final PaisService paisService;

    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista com todos os países e seus Códigos", description = "Exemplo: BR - Brasil")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de países formatada retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(type = "string", example = "BR - Brazil")))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro ao comunicar-se com a API externa de geolocalização",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public List<String> listarPaises(){
        return paisService.listarTodos();
    }

    @GetMapping("/{codigoPais}")
    @Operation(
            summary = "Retorna informações do país inserido",
            description = "Retorna nome oficial, código ISO-2, latitude e longitude a partir da integração com a API Country State City."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Detalhes geográficos do país retornados com êxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaisDetalhesResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Código de país informado em branco ou inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorValidation.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Falha ao obter dados do país junto à API externa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public PaisDetalhesResponse exibePais(
            @Parameter(description = "Código ISO-2 do país (2 caracteres)", example = "BR", required = true)
            @PathVariable @NotBlank String codigoPais){
        return paisService.exibirPais(codigoPais);
    }
}
