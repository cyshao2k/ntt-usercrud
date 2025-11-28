package com.ntt.evaluation.user_manager.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
        HttpServletRequest request, 
        HttpServletResponse response, 
        AuthenticationException authException) throws IOException {

        // 1. Establecer el código de estado HTTP a 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        // 2. Definir el tipo de contenido como JSON
        response.setContentType("application/json");
        
        // 3. Escribir el cuerpo del mensaje de error (usando su esquema Error)
        // Nota: Adaptar esto para que coincida con su modelo Error.class de OpenAPI
        String errorMessage = String.format(
            "{\"mensaje\": \"Acceso no autorizado. %s\"}", 
            authException.getMessage()
        );
        
        response.getWriter().write(errorMessage);
    }
}