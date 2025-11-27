package com.ntt.evaluation.user_manager.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ntt.evaluation.user_manager.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Inyección de dependencia de su repositorio de usuarios
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Este método es llamado por Spring Security para obtener la información de un usuario
     * dado su nombre de usuario.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 1. Buscar el usuario en la base de datos (usando el nombre de usuario extraído del JWT)
        // Sustituir `findByUsername` por el método real de su repositorio.
        //Optional<User> userOptional = userRepository.findByUsername(username);

        // 2. Lanzar excepción si no se encuentra (necesario para Spring Security)
        //User user = userOptional.orElseThrow(() -> 
        //    new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username)
        //);

        // 3. Convertir la información del modelo de usuario a un objeto UserDetails de Spring Security
        //return new org.springframework.security.core.userdetails.User(
        //    user.getUsername(),           // Nombre de usuario
        //    user.getPassword(),           // Contraseña (no es necesaria para JWT, pero Spring la requiere)
        //    user.getAuthorities()         // Colección de Roles/Permisos (GrantedAuthority)
        //);

        System.out.println("Paso por aca");

        Collection<? extends GrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_USER"),
                    new SimpleGrantedAuthority("PERMISSION_A")
                );

        org.springframework.security.core.userdetails.User userOptional = new org.springframework.security.core.userdetails.User(
            "1234567890",
            "123",
            authorities
        );



        return userOptional;
        
        /* * NOTA: `user.getAuthorities()` debe devolver un Collection<GrantedAuthority>
         * Si su modelo `User` solo tiene una lista de roles (ej: String), deberá convertirla
         * usando SimpleGrantedAuthority, por ejemplo:
         * * .map(role -> new SimpleGrantedAuthority(role.getName()))
         * .collect(Collectors.toList());
         */
    }
}