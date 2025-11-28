package com.ntt.evaluation.user_manager.repository;

import com.ntt.evaluation.user_manager.repository.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * Útil para el proceso de login y validación de unicidad.
     * @param email La dirección de email a buscar.
     * @return Un Optional que puede contener el Usuario si existe.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si existe un usuario con un correo electrónico específico.
     * @param email La dirección de email a verificar.
     * @return true si existe un usuario con ese email, false en caso contrario.
     */
    boolean existsByEmail(String email);
}
