package com.ntt.evaluation.user_manager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil; // Clase utilitaria para manejar el JWT (debe crearla)
    private final CustomUserDetailsService userDetailsService; // Servicio para cargar el usuario (debe crearla)

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, CustomUserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. Comprobar si existe el header de autorización con formato Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraer el token
        jwt = authHeader.substring(7); // "Bearer " tiene 7 caracteres

        // 3. Extraer el nombre de usuario (o ID) del token
        // NOTA: Asumimos que JwtTokenUtil.extractUsername() maneja las excepciones de token no válido
        try {
            username = jwtTokenUtil.extractUsername(jwt);
        } catch (Exception e) {
            // Logear el error (token inválido/expirado) si es necesario
            filterChain.doFilter(request, response);
            return;
        }

        // 4. Si el nombre de usuario es válido y AÚN NO está autenticado
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // 5. Cargar detalles del usuario (roles/permisos)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 6. Validar el token y el usuario
            if (jwtTokenUtil.validateToken(jwt, userDetails)) {
                
                // 7. Crear el objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, 
                        userDetails.getAuthorities() // Los roles/permisos del usuario
                );
                
                // 8. Establecer detalles de la petición (IP, sesión si hubiera, etc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                
                // 9. Establecer la autenticación en el Contexto de Seguridad
                // Esto es lo que hace que Spring considere al usuario como "autenticado"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}