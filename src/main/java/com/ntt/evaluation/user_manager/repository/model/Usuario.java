package com.ntt.evaluation.user_manager.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "USUARIO")
public class Usuario {

    @Id
    // Hibernate generará el UUID automáticamente antes de persistir
    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "usuario_id", updatable = false, nullable = false)
    private UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Column(name = "nombre", nullable = false, length = 100) // Ajusta la longitud si es necesario
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "email", nullable = false, updatable = false, unique = true, length = 100)
    @Email // Anotación de validación
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false, length = 256)
    @Size(min = 8) // Anotación de validación
    private String password;
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // El campo creado y modificado los gestionaremos nosotros o con Listeners
    @Column(name = "creado", nullable = false, updatable = false)
    private OffsetDateTime creado;

    public OffsetDateTime getCreado() {
        return creado;
    }

    public void setCreado(OffsetDateTime creado) {
        this.creado = creado;
    }

    @Column(name = "modificado")
    private OffsetDateTime modificado;

    public OffsetDateTime getModificado() {
        return modificado;
    }

    public void setModificado(OffsetDateTime modificado) {
        this.modificado = modificado;
    }

    @Column(name = "ultimo_login", nullable = false)
    private OffsetDateTime ultimoLogin;

    public OffsetDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(OffsetDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    @Column(name = "token", nullable = false, length = 2000)
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    // Relación One-to-Many con Telefono (Un usuario a Muchos teléfonos)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Telefono> telefonos = new ArrayList<>();

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }


}
