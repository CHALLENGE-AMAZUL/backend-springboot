package com.amazul.fiap.api_springboot.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String recurso, Long id) {
        super(recurso + " de id (" + id + ") não encontrado!");
    }
}
