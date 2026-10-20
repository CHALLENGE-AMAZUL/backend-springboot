package com.amazul.fiap.api_springboot.config;

import com.amazul.fiap.api_springboot.dto.errors.ErrorResponse;
import com.amazul.fiap.api_springboot.dto.errors.ErrorValidation;
import com.amazul.fiap.api_springboot.dto.errors.FieldError;
import com.amazul.fiap.api_springboot.exception.ResourceNotFoundException;
import com.amazul.fiap.api_springboot.exception.TransicaoInvalidaStatusException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerRecursoNaoEncontrado(ResourceNotFoundException ex){
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorValidation> handlerArgumentoInvalido(MethodArgumentNotValidException ex){

        List<FieldError> camposErros = ex.getBindingResult().getFieldErrors().stream()
                .map(campoErro -> new FieldError(campoErro.getField(), campoErro.getDefaultMessage()))
                .toList();

        List<FieldError> errosGlobais = ex.getBindingResult().getGlobalErrors().stream()
                .map(erroGlobal -> new FieldError(erroGlobal.getObjectName(), erroGlobal.getDefaultMessage()))
                .toList();

        List<FieldError> erros = Stream.concat(camposErros.stream(), errosGlobais.stream()).toList();

        ErrorValidation bodyErros = new ErrorValidation(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value(),
                erros,
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(bodyErros);
    }

    @ExceptionHandler(TransicaoInvalidaStatusException.class)
    public ResponseEntity<ErrorResponse> handlerTransicaoInvalidaStatus(TransicaoInvalidaStatusException ex){
        return buildError(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handlerParametroInvalido(MethodArgumentTypeMismatchException ex){
        return buildError(HttpStatus.BAD_REQUEST, "Parâmetro: (" +  ex.getName() + ") inválido!");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handlerConstraintValidation(ConstraintViolationException ex){
        String mensagem = ex.getConstraintViolations().stream()
                .map(valor -> valor.getPropertyPath() + ": " + valor.getMessage())
                .collect(Collectors.joining(";"));
        return buildError(HttpStatus.BAD_REQUEST, mensagem);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorValidation> handlerValidation(HandlerMethodValidationException ex){
        List<FieldError> errosGlobais = ex.getParameterValidationResults().getFirst().getResolvableErrors()
                .stream()
                .map(erroGlobal -> new FieldError(
                        "Validação",
                        erroGlobal.getDefaultMessage()
                ))
                .toList();

        ErrorValidation bodyErros = new ErrorValidation(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value(),
                errosGlobais,
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(bodyErros);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerGenerico(Exception ex){
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inesperado.");
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus statusCode, String mensagem){
        return ResponseEntity.status(statusCode).body(
                new ErrorResponse(mensagem, statusCode.getReasonPhrase(), statusCode.value(), LocalDateTime.now()));
    }
}
