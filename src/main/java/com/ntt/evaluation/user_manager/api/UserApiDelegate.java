package com.ntt.evaluation.user_manager.api;

import com.ntt.evaluation.user_manager.model.Error;
import java.util.UUID;
import com.ntt.evaluation.user_manager.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link UserApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-26T23:12:16.846028-03:00[America/Santiago]", comments = "Generator version: 7.8.0")
public interface UserApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /user : Crea un nuevo usuario
     * Crea un nuevo usuario
     *
     * @param user Crea un nuevo usuario (required)
     * @return Successful operation (status code 200)
     *         or Invalid input (status code 405)
     *         or Unexpected error (status code 200)
     * @see UserApi#addUser
     */
    default ResponseEntity<User> addUser(User user) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"password\" : \"password\", \"creado\" : \"2000-01-23T04:56:07.000+00:00\", \"correo\" : \"correo\", \"ultimoLogin\" : \"2000-01-23T04:56:07.000+00:00\", \"modificado\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"telefonos\" : [ { \"numero\" : \"numero\", \"codigoCiudad\" : \"codigoCiudad\", \"codigoPais\" : \"codigoPais\" }, { \"numero\" : \"numero\", \"codigoCiudad\" : \"codigoCiudad\", \"codigoPais\" : \"codigoPais\" } ], \"nombre\" : \"nombre\", \"token\" : \"token\", \"activo\" : \"activo\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /user/{userId} : Borra un usuario existente
     * Borra un usuario existente
     *
     * @param userId Id del usuario a obtener (required)
     * @return Successful operation (status code 200)
     *         or Usuario no Encontrado (status code 404)
     *         or Invalid input (status code 405)
     *         or Unexpected error (status code 200)
     * @see UserApi#deleteUser
     */
    default ResponseEntity<Void> deleteUser(UUID userId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"mensaje\" : \"mensaje\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /user/{userId} : Obtiene un usuario por su id
     * Retorna. el usuario correspondiente al id
     *
     * @param userId Id del usuario a obtener (required)
     * @return El usuario (status code 200)
     *         or Usuario no Encontrado (status code 404)
     *         or Unexpected error (status code 200)
     * @see UserApi#getUserById
     */
    default ResponseEntity<User> getUserById(UUID userId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"password\" : \"password\", \"creado\" : \"2000-01-23T04:56:07.000+00:00\", \"correo\" : \"correo\", \"ultimoLogin\" : \"2000-01-23T04:56:07.000+00:00\", \"modificado\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"telefonos\" : [ { \"numero\" : \"numero\", \"codigoCiudad\" : \"codigoCiudad\", \"codigoPais\" : \"codigoPais\" }, { \"numero\" : \"numero\", \"codigoCiudad\" : \"codigoCiudad\", \"codigoPais\" : \"codigoPais\" } ], \"nombre\" : \"nombre\", \"token\" : \"token\", \"activo\" : \"activo\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"mensaje\" : \"mensaje\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /user : Lista los usarios del sistema
     * Lista los usarios del sistema
     *
     * @return Successful operation (status code 200)
     *         or No se encuentran usuarios (status code 404)
     *         or Unexpected error (status code 200)
     * @see UserApi#listUsers
     */
    default ResponseEntity<User> listUsers() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"password\" : \"password\", \"creado\" : \"2000-01-23T04:56:07.000+00:00\", \"correo\" : \"correo\", \"ultimoLogin\" : \"2000-01-23T04:56:07.000+00:00\", \"modificado\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"telefonos\" : [ { \"numero\" : \"numero\", \"codigoCiudad\" : \"codigoCiudad\", \"codigoPais\" : \"codigoPais\" }, { \"numero\" : \"numero\", \"codigoCiudad\" : \"codigoCiudad\", \"codigoPais\" : \"codigoPais\" } ], \"nombre\" : \"nombre\", \"token\" : \"token\", \"activo\" : \"activo\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"mensaje\" : \"mensaje\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /user/{userId} : Actualiza un usuario existente
     * Actualiza un usuario existente
     *
     * @param userId Id del usuario a obtener (required)
     * @param user Actualiza un usuario existente (required)
     * @return Successful operation (status code 201)
     *         or Invalid input (status code 405)
     *         or Unexpected error (status code 200)
     * @see UserApi#updateUser
     */
    default ResponseEntity<Void> updateUser(UUID userId,
        User user) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
