package com.amazul.fiap.api_springboot.controller;

import com.amazul.fiap.api_springboot.dto.response.LocalResponse;
import com.amazul.fiap.api_springboot.dto.response.PageResponse;
import com.amazul.fiap.api_springboot.service.LocalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locais")
@Tag(name = "Local", description = "Endpoints dos locais que envolvem as informações sobre tecnologia nuclear.")
public class LocalController {
    private final LocalService localService;

    public LocalController(LocalService localService){
        this.localService = localService;
    }

    @GetMapping
    public PageResponse<LocalResponse> listar(@RequestParam(required = false, defaultValue = "0") Integer pagina,
                                              @RequestParam(required = false, defaultValue = "10") Integer tamanho){
        return PageResponse.from(localService.listarTodos(pagina, tamanho));
    }
}
