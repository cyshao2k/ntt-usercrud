package com.ntt.evaluation.user_manager.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "TELEFONO") // Asume que creaste una tabla TELEFONOS
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // ID autoincremental para el teléfono en sí
    @Column(name = "telefono_id", updatable = false, nullable = false)
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(name = "numero", nullable = false)
    @Size(max = 20)
    private String numero;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Column(name = "codigo_ciudad", nullable = false)
    @Size(max = 5)
    private String citycode;

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    @Column(name = "codigo_pais", nullable = false)
    @Size(max = 5)
    private String contryCode;
    
    public String getContryCode() {
        return contryCode;
    }

    public void setContryCode(String contryCode) {
        this.contryCode = contryCode;
    }

    // Relación Many-to-One con Usuario (Muchos teléfonos a Un usuario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false) // Esta es la Foreign Key (FK) en la DB
    @JsonIgnore // Importante para evitar bucles infinitos al serializar a JSON
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
