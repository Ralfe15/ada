package com.eventmaster.ticketsales.presentation.controller;

import com.eventmaster.ticketsales.infrastructure.security.JwtTokenUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller de autenticação (endpoint público).
 * Gera um token JWT para o usuário — simplificado para fins acadêmicos.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * POST /auth/login?username=user
     * Retorna um token JWT. Em produção, validaria credenciais contra um banco de dados.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username) {
        String token = jwtTokenUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
