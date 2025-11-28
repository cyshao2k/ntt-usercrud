package com.ntt.evaluation.user_manager.srvs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ntt.evaluation.user_manager.model.User;
import com.ntt.evaluation.user_manager.repository.UsuarioRepository;
import com.ntt.evaluation.user_manager.repository.model.Usuario;
import com.ntt.evaluation.user_manager.security.JwtTokenUtil;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Test
    public void whenGetUserById_thenReturnUser() {

        Usuario usuario = new Usuario();
        usuario.setUserId(UUID.randomUUID());
        usuario.setNombre("Nombre1");
        usuario.setEmail("correo@valido.com");
        usuario.setPassword("1234567");
        usuario.setToken("TOKEN");
        usuario.setActivo(true);
        usuario.setCreado(OffsetDateTime.now());
        usuario.setUltimoLogin(OffsetDateTime.now());

        Optional<Usuario> usuarioOpt = Optional.of(usuario);

        User user = new User();
        user.setId(usuario.getUserId());
        user.setNombre("Nombre1");
        user.setCorreo("correo@valido.com");
        user.setPassword("1234567");
        user.setToken("TOKEN");


        Mockito.when(usuarioRepository.findById(any())).thenReturn(usuarioOpt);

        ResponseEntity<User> response = userServiceImpl.getUserById(usuario.getUserId());

        User reponseUser = response.getBody();
        assertEquals(user.getId(), reponseUser.getId());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenAddUser_thenReturnUser() {

        Usuario usuario = new Usuario();
        usuario.setUserId(UUID.randomUUID());
        usuario.setNombre("Nombre1");
        usuario.setEmail("correo@valido.com");
        usuario.setPassword("1234567");
        usuario.setToken("TOKEN");
        usuario.setActivo(true);
        usuario.setCreado(OffsetDateTime.now());
        usuario.setUltimoLogin(OffsetDateTime.now());

        User user = new User();
        user.setId(usuario.getUserId());
        user.setNombre("Nombre1");
        user.setCorreo("correo@valido.com");
        user.setPassword("1234567");
        user.setToken("TOKEN");


        Mockito.when(usuarioRepository.save(any())).thenReturn(usuario);

        ResponseEntity<User> response = userServiceImpl.addUser(user);

        User reponseUser = response.getBody();
        assertEquals(user.getId(), reponseUser.getId());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenDeleteUser_thenReturnOk() {

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setNombre("Nombre1");
        user.setCorreo("correo@valido.com");
        user.setPassword("1234567");
        user.setToken("TOKEN");


        //Mockito.when(usuarioRepository.delete(any())).thenReturn(user);
        Mockito.doNothing().when(usuarioRepository).delete(any());

        ResponseEntity<Void> response = userServiceImpl.deleteUser(user.getId());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    
}
