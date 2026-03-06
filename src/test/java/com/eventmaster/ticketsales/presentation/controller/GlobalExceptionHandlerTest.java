package com.eventmaster.ticketsales.presentation.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Testes unitarios para o tratamento global de excecoes.
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Deve retornar 400 para IllegalArgumentException")
    void shouldReturn400ForIllegalArgument() {
        // Arrange
        var ex = new IllegalArgumentException("Evento nao encontrado");

        // Act
        ResponseEntity<Map<String, Object>> response = handler.handleIllegalArgument(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().get("status"));
        assertEquals("Evento nao encontrado", response.getBody().get("error"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    @DisplayName("Deve retornar 409 para IllegalStateException")
    void shouldReturn409ForIllegalState() {
        // Arrange
        var ex = new IllegalStateException("Ingressos insuficientes");

        // Act
        ResponseEntity<Map<String, Object>> response = handler.handleIllegalState(ex);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(409, response.getBody().get("status"));
        assertEquals("Ingressos insuficientes", response.getBody().get("error"));
    }

    @Test
    @DisplayName("Deve retornar 400 com lista de erros para validacao")
    @SuppressWarnings("unchecked")
    void shouldReturn400WithFieldErrors() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("request", "customerEmail", "E-mail invalido.");
        FieldError fieldError2 = new FieldError("request", "quantity", "A quantidade deve ser pelo menos 1.");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<Map<String, Object>> response = handler.handleValidation(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().get("status"));

        List<String> errors = (List<String>) response.getBody().get("errors");
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.stream().anyMatch(e -> e.contains("customerEmail")));
        assertTrue(errors.stream().anyMatch(e -> e.contains("quantity")));
    }

    @Test
    @DisplayName("Deve retornar 500 com mensagem generica para excecoes nao tratadas")
    void shouldReturn500ForGenericException() {
        // Arrange
        var ex = new RuntimeException("Erro inesperado interno");

        // Act
        ResponseEntity<Map<String, Object>> response = handler.handleGeneral(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().get("status"));
        assertEquals("Erro interno do servidor.", response.getBody().get("error"));
    }
}
