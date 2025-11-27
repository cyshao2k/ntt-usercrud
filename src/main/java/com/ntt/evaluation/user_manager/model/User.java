package com.ntt.evaluation.user_manager.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ntt.evaluation.user_manager.model.UserTelefonosInner;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Usuario
 */

@Schema(name = "User", description = "Usuario")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-26T23:12:16.846028-03:00[America/Santiago]", comments = "Generator version: 7.8.0")
public class User {

  private UUID id;

  private String nombre;

  private String correo;

  private String password;

  @Valid
  private List<@Valid UserTelefonosInner> telefonos = new ArrayList<>();

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime creado;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime modificado;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime ultimoLogin;

  private String token;

  private String activo;

  public User id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Id del usuario
   * @return id
   */
  @Valid 
  @Schema(name = "id", description = "Id del usuario", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public User nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Get nombre
   * @return nombre
   */
  
  @Schema(name = "nombre", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nombre")
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public User correo(String correo) {
    this.correo = correo;
    return this;
  }

  /**
   * Get correo
   * @return correo
   */
  @jakarta.validation.constraints.Email 
  @Schema(name = "correo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("correo")
  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public User password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   */
  
  @Schema(name = "password", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public User telefonos(List<@Valid UserTelefonosInner> telefonos) {
    this.telefonos = telefonos;
    return this;
  }

  public User addTelefonosItem(UserTelefonosInner telefonosItem) {
    if (this.telefonos == null) {
      this.telefonos = new ArrayList<>();
    }
    this.telefonos.add(telefonosItem);
    return this;
  }

  /**
   * Get telefonos
   * @return telefonos
   */
  @Valid 
  @Schema(name = "telefonos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("telefonos")
  public List<@Valid UserTelefonosInner> getTelefonos() {
    return telefonos;
  }

  public void setTelefonos(List<@Valid UserTelefonosInner> telefonos) {
    this.telefonos = telefonos;
  }

  public User creado(OffsetDateTime creado) {
    this.creado = creado;
    return this;
  }

  /**
   * Get creado
   * @return creado
   */
  @Valid 
  @Schema(name = "creado", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("creado")
  public OffsetDateTime getCreado() {
    return creado;
  }

  public void setCreado(OffsetDateTime creado) {
    this.creado = creado;
  }

  public User modificado(OffsetDateTime modificado) {
    this.modificado = modificado;
    return this;
  }

  /**
   * Get modificado
   * @return modificado
   */
  @Valid 
  @Schema(name = "modificado", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("modificado")
  public OffsetDateTime getModificado() {
    return modificado;
  }

  public void setModificado(OffsetDateTime modificado) {
    this.modificado = modificado;
  }

  public User ultimoLogin(OffsetDateTime ultimoLogin) {
    this.ultimoLogin = ultimoLogin;
    return this;
  }

  /**
   * Get ultimoLogin
   * @return ultimoLogin
   */
  @Valid 
  @Schema(name = "ultimoLogin", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ultimoLogin")
  public OffsetDateTime getUltimoLogin() {
    return ultimoLogin;
  }

  public void setUltimoLogin(OffsetDateTime ultimoLogin) {
    this.ultimoLogin = ultimoLogin;
  }

  public User token(String token) {
    this.token = token;
    return this;
  }

  /**
   * Get token
   * @return token
   */
  
  @Schema(name = "token", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("token")
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public User activo(String activo) {
    this.activo = activo;
    return this;
  }

  /**
   * Get activo
   * @return activo
   */
  
  @Schema(name = "activo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("activo")
  public String getActivo() {
    return activo;
  }

  public void setActivo(String activo) {
    this.activo = activo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.id, user.id) &&
        Objects.equals(this.nombre, user.nombre) &&
        Objects.equals(this.correo, user.correo) &&
        Objects.equals(this.password, user.password) &&
        Objects.equals(this.telefonos, user.telefonos) &&
        Objects.equals(this.creado, user.creado) &&
        Objects.equals(this.modificado, user.modificado) &&
        Objects.equals(this.ultimoLogin, user.ultimoLogin) &&
        Objects.equals(this.token, user.token) &&
        Objects.equals(this.activo, user.activo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, correo, password, telefonos, creado, modificado, ultimoLogin, token, activo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    correo: ").append(toIndentedString(correo)).append("\n");
    sb.append("    password: ").append("*").append("\n");
    sb.append("    telefonos: ").append(toIndentedString(telefonos)).append("\n");
    sb.append("    creado: ").append(toIndentedString(creado)).append("\n");
    sb.append("    modificado: ").append(toIndentedString(modificado)).append("\n");
    sb.append("    ultimoLogin: ").append(toIndentedString(ultimoLogin)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    activo: ").append(toIndentedString(activo)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

