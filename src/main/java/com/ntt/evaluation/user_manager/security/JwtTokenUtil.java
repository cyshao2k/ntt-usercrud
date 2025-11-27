package com.ntt.evaluation.user_manager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    // Se inyecta la clave secreta desde application.properties
    // EJEMPLO: jwt.secret=TU_CLAVE_SECRETA_LARGA_AQUI_MINIMO_32_BYTES
    @Value("${jwt.secret}")
    private String secret;

    // Tiempo de vida del token en milisegundos (ej: 24 horas)
    @Value("${jwt.expiration}")
    private long jwtExpiration; 

    // -----------------------------------------------------------------
    // 1. Métodos de Extracción
    // -----------------------------------------------------------------

    /** Extrae el nombre de usuario (subject) del token. */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /** Extrae una "claim" específica del token. */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /** Extrae todas las "claims" (carga útil) del token. */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // -----------------------------------------------------------------
    // 2. Métodos de Generación
    // -----------------------------------------------------------------

    /** Genera un token basado en los detalles de usuario. */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /** Genera un token añadiendo "claims" extra. */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Aplica el tiempo de expiración
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // -----------------------------------------------------------------
    // 3. Métodos de Validación
    // -----------------------------------------------------------------

    /** Valida si el token es válido y no ha expirado. */
    public boolean validateToken(String token, UserDetails userDetails) {

        System.out.println("Secret["+secret+"]");

        System.out.println("*********");
        System.out.println("Token["+token+"]");
        System.out.println("*********");

        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /** Comprueba si el token ha expirado. */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /** Extrae la fecha de expiración. */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // -----------------------------------------------------------------
    // 4. Utilidades Criptográficas
    // -----------------------------------------------------------------

    /** Obtiene la clave de firma decodificada. */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}