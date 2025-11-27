package com.ntt.evaluation.user_manager.config;

import com.ntt.evaluation.user_manager.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Inyectamos nuestro filtro personalizado
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Deshabilitar CSRF (común para APIs REST sin sesiones)
            .csrf(csrf -> csrf.disable())
            
            // 2. Configurar la política de sesión como STATELESS (sin estado)
            // Esto asegura que cada solicitud debe tener el JWT
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 3. Definir la autorización para las peticiones
            .authorizeHttpRequests(auth -> auth
                // Permitir acceso sin autenticación a endpoints públicos (ej: login)
                .requestMatchers("/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // Requerir autenticación para cualquier otra ruta
                .anyRequest().authenticated()
            )
            
            // 4. Añadir nuestro filtro JWT antes del filtro estándar de Spring
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}