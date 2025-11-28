package com.ntt.evaluation.user_manager.security;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ntt.evaluation.user_manager.repository.UsuarioRepository;
import com.ntt.evaluation.user_manager.repository.model.Usuario;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    //private final JWTUserRepository usuarioRepository;
    private final UsuarioRepository usuarioRepository;

    // Inyección de dependencia de su repositorio de usuarios
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Este método es llamado por Spring Security para obtener la información de un usuario
     * dado su nombre de usuario.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 1. Buscar el usuario en la base de datos (usando el nombre de usuario extraído del JWT)
        // Sustituir `findByUsername` por el método real de su repositorio.
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(username);


        // 2. Lanzar excepción si no se encuentra (necesario para Spring Security)
        if (!usuarioEncontrado.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username);
        }
        Usuario user = usuarioEncontrado.get();

        Collection<? extends GrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_USER")
                );
        
        // 3. Convertir la información del modelo de usuario a un objeto UserDetails de Spring Security
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),           // Nombre de usuario
            user.getPassword(),           // Contraseña (no es necesaria para JWT, pero Spring la requiere)
            authorities         // Colección de Roles/Permisos (GrantedAuthority)
        );
    }
        
}