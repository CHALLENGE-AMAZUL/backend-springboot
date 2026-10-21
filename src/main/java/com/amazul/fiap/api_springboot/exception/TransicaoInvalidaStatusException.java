package com.amazul.fiap.api_springboot.exception;

public class TransicaoInvalidaStatusException extends RuntimeException {
    public TransicaoInvalidaStatusException(String mensagem) {
        super(mensagem);
    }
}
