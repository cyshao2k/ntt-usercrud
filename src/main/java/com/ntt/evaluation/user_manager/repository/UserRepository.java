package com.ntt.evaluation.user_manager.repository;

import com.ntt.evaluation.user_manager.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Spring Data JPA genera automáticamente la implementación de este método
     * al interpretar el nombre "findByUsername".
     * * Este es el método clave que usa CustomUserDetailsService.
     */
    Optional<User> findByUsername(String username);

    // Si necesitaran verificar la existencia:
    boolean existsByUsername(String username);
}