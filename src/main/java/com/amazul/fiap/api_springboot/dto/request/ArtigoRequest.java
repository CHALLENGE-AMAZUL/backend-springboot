package com.amazul.fiap.api_springboot.dto.request;

import com.amazul.fiap.api_springboot.model.AreaAplicacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record ArtigoRequest(
        @NotBlank(message = "Título deve ser inserido!")
        @Size(max = 150, message = "Máximo de 150 caracteres")
        String titulo,

        @NotBlank(message = "Resumo deve ser inserido!")
        @Size(max = 150, message = "Máximo de 150 caracteres")
        String resumo,

        @NotBlank(message = "Aplicação deve ser inserida!")
        @Size(max = 150, message = "Máximo de 150 caracteres")
        String aplicacaoReal,

        @NotBlank(message = "Impacto na sociedade não pode ser vazio!")
        @Size(max = 150, message = "Máximo de 150 caracteres")
        String impactoSociedade,

        @NotNull(message = "Área deve ser inserida")
        @Schema(example = "SAUDE")
        AreaAplicacao area

        //private Local local;
        //List<ArtigoFonte> referencias;
){
}
