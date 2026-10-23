package com.amazul.fiap.api_springboot.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro de uma localização geográfica de uma aplicação da tecnologia nuclear")
public record LocalRequest(
        @Schema(
                description = "Nome da instituição, usina, reator, centro de pesquisa ou contexto da aplicação",
                example = "Complexo radiológico de Iperó/Aramar (CTMSP)",
                maxLength = 150,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "O nome do local não pode ser vazio")
        @Size(max = 150, message = "O nome do local deve ter no máximo 150 caracteres.")
        String nome,

        @Schema(
                description = "Código ISO-2 do país onde está situado. Pode ser nulo para aplicações extraterrestres ou águas internacionais (ex.: Sonda Voyager, alto-mar)",
                example = "BR",
                nullable = true,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Pattern(regexp = "^[A-Z]{2}$", message = "O código do país deve seguir o padrão ISO-2 com 2 letras maiúsculas (ex: BR, US)")
        String codigoPais) {
}
