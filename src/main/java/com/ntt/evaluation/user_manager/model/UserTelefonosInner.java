package com.ntt.evaluation.user_manager.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * UserTelefonosInner
 */

@JsonTypeName("User_telefonos_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-27T17:18:50.367913-03:00[America/Santiago]", comments = "Generator version: 7.8.0")
public class UserTelefonosInner {

  private String numero;

  private String codigoCiudad;

  private String codigoPais;

  public UserTelefonosInner numero(String numero) {
    this.numero = numero;
    return this;
  }

  /**
   * Get numero
   * @return numero
   */
  
  @Schema(name = "numero", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("numero")
  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public UserTelefonosInner codigoCiudad(String codigoCiudad) {
    this.codigoCiudad = codigoCiudad;
    return this;
  }

  /**
   * Get codigoCiudad
   * @return codigoCiudad
   */
  
  @Schema(name = "codigoCiudad", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("codigoCiudad")
  public String getCodigoCiudad() {
    return codigoCiudad;
  }

  public void setCodigoCiudad(String codigoCiudad) {
    this.codigoCiudad = codigoCiudad;
  }

  public UserTelefonosInner codigoPais(String codigoPais) {
    this.codigoPais = codigoPais;
    return this;
  }

  /**
   * Get codigoPais
   * @return codigoPais
   */
  
  @Schema(name = "codigoPais", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("codigoPais")
  public String getCodigoPais() {
    return codigoPais;
  }

  public void setCodigoPais(String codigoPais) {
    this.codigoPais = codigoPais;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserTelefonosInner userTelefonosInner = (UserTelefonosInner) o;
    return Objects.equals(this.numero, userTelefonosInner.numero) &&
        Objects.equals(this.codigoCiudad, userTelefonosInner.codigoCiudad) &&
        Objects.equals(this.codigoPais, userTelefonosInner.codigoPais);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numero, codigoCiudad, codigoPais);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserTelefonosInner {\n");
    sb.append("    numero: ").append(toIndentedString(numero)).append("\n");
    sb.append("    codigoCiudad: ").append(toIndentedString(codigoCiudad)).append("\n");
    sb.append("    codigoPais: ").append(toIndentedString(codigoPais)).append("\n");
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

