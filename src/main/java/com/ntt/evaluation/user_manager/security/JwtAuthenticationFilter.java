package com.ntt.evaluation.user_manager.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

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
            
            try {
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
            } catch (UsernameNotFoundException ex) {
                // Esta excepción DEBERÍA ser capturada por Spring Security, 
                // pero si no lo hace, podemos forzar el manejo aquí.
                
                // Simplemente dejamos que el filtro continúe para que la cadena se rompa
                // en el punto de fallo de autenticación de Spring Security, 
                // o forzamos el error.

                if (logger.isErrorEnabled()) {
                    logger.error("Error de JWT: Usuario no encontrado en la DB. " + ex.getMessage(), ex);
                }
                
            } catch (ExpiredJwtException | MalformedJwtException ex) {
                // 🔑 CASO CRÍTICO: Excepciones de token mal formado/caducado
                
                // Si el token es inválido, forzamos al AuthenticationEntryPoint a ejecutarse.
                // Para hacer esto de forma limpia, DEBEMOS lanzar una AuthenticationException
                // para que Spring Security la intercepte después de que el filtro termine,
                // o llamar a un componente que haga el manejo.
                
                // La mejor solución es DELEGAR el manejo al EntryPoint si la validación falla
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(String.format("{\"mensaje\": \"Token inválido o expirado. %s\"}", ex.getMessage()));
                return; // Detener la cadena de filtros aquí
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}