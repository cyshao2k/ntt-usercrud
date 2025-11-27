package com.ntt.evaluation.user_manager.repository.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection; // Usaremos esta interfaz para la autoridad

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password; // Debe ser el hash de la contraseña

    // Otros campos (email, nombre, activo, etc.)

    // Relación con Roles/Authorities, si aplica.
    // Ejemplo simple: 
    @Transient // Indica que este campo no se mapea a la BD
    private Collection<? extends GrantedAuthority> authorities; 

    // Constructor, Getters y Setters...
    
    // Método para obtener las autoridades (roles)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    // ... (Métodos necesarios para JPA)

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}