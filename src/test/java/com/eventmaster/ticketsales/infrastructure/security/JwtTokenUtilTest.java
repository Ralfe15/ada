package com.eventmaster.ticketsales.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes unitarios para o utilitario de JWT.
 * Instancia diretamente sem Spring Context para manter os testes rapidos.
 */
class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;

    // Chave com pelo menos 256 bits para HMAC-SHA
    private static final String SECRET = "EventMasterSecretKey2024ADAProjetoFinalChaveSegura256bits!";
    private static final long EXPIRATION_MS = 3600000; // 1 hora

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil(SECRET, EXPIRATION_MS);
    }

    @Test
    @DisplayName("Deve gerar um token JWT nao nulo")
    void shouldGenerateNonNullToken() {
        String token = jwtTokenUtil.generateToken("joao");
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    @DisplayName("Deve extrair o username correto do token")
    void shouldExtractUsernameFromToken() {
        // Arrange
        String username = "maria@email.com";
        String token = jwtTokenUtil.generateToken(username);

        // Act
        String extracted = jwtTokenUtil.getUsernameFromToken(token);

        // Assert
        assertEquals(username, extracted);
    }

    @Test
    @DisplayName("Deve validar um token valido como true")
    void shouldValidateValidToken() {
        String token = jwtTokenUtil.generateToken("admin");
        assertTrue(jwtTokenUtil.validateToken(token));
    }

    @Test
    @DisplayName("Deve invalidar um token adulterado")
    void shouldInvalidateTamperedToken() {
        String token = jwtTokenUtil.generateToken("admin");
        // Adultera o token alterando o ultimo caractere
        String tampered = token.substring(0, token.length() - 1) + "X";
        assertFalse(jwtTokenUtil.validateToken(tampered));
    }

    @Test
    @DisplayName("Deve invalidar um token com formato invalido")
    void shouldInvalidateMalformedToken() {
        assertFalse(jwtTokenUtil.validateToken("token.invalido.aqui"));
    }

    @Test
    @DisplayName("Deve invalidar um token expirado")
    void shouldInvalidateExpiredToken() {
        // Cria um JwtTokenUtil com expiracao de 0ms (token ja nasce expirado)
        JwtTokenUtil expiredUtil = new JwtTokenUtil(SECRET, 0);
        String token = expiredUtil.generateToken("user");

        // Token com expiracao 0 pode estar expirado imediatamente
        // Aguarda 1ms para garantir que expirou
        try { Thread.sleep(1); } catch (InterruptedException ignored) {}

        assertFalse(expiredUtil.validateToken(token));
    }
}
