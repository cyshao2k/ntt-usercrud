package com.ntt.evaluation.user_manager.srvs;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ntt.evaluation.user_manager.api.UserApiDelegate;
import com.ntt.evaluation.user_manager.model.User;
import com.ntt.evaluation.user_manager.model.UserTelefonosInner;
import com.ntt.evaluation.user_manager.repository.UsuarioRepository;
import com.ntt.evaluation.user_manager.repository.model.Telefono;
import com.ntt.evaluation.user_manager.repository.model.Usuario;
import com.ntt.evaluation.user_manager.security.JwtTokenUtil;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserApiDelegate {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${validation.pattern.email}")
    private String emailValidationPattern;

    private Pattern emailPATTERN;

    @Value("${validation.pattern.password}")
    private String passwordValidationPattern;

    private Pattern passwordPATTERN;

    private final UsuarioRepository usuarioRepository;

    private final JwtTokenUtil jwtTokenUtil;

    public UserServiceImpl(UsuarioRepository usuarioRepository, JwtTokenUtil jwtTokenUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostConstruct
    public void inicializa() {
        this.emailPATTERN = Pattern.compile(this.emailValidationPattern);
        this.passwordPATTERN = Pattern.compile(this.passwordValidationPattern);
    }

    @Override
    public ResponseEntity<User> addUser(User user) {
        
        if (logger.isDebugEnabled()) {
            logger.debug("UserServiceImpl Add User");
        }

        if (logger.isInfoEnabled()) {
            logger.info(user.toString());
            logger.info("emailValidationPattern["+this.emailValidationPattern+"]");
        }

        if (user.getCorreo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo no puede ser nulo");
        }

        if (this.emailPATTERN==null) this.emailPATTERN = Pattern.compile("^.*$");
        Matcher emailMatcher = emailPATTERN.matcher(user.getCorreo());
        if (!emailMatcher.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo no valido " + user.getCorreo());
        }

        if (usuarioRepository.existsByEmail(user.getCorreo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo ya existe");
        }

        if (user.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password no puede ser nula");
        }

        if (this.passwordPATTERN==null) this.passwordPATTERN = Pattern.compile("^.*$");
        Matcher passwordMatcher = passwordPATTERN.matcher(user.getPassword());
        if (!passwordMatcher.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password no valida");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(user.getNombre());
        usuario.setEmail(user.getCorreo());
        usuario.setPassword(user.getPassword());
        usuario.setToken(this.generateToken(user));
        usuario.setActivo(true);
        usuario.setCreado(OffsetDateTime.now());
        usuario.setUltimoLogin(OffsetDateTime.now());

        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        List<Telefono> telefonos = this.getTelefonos(user.getTelefonos());        

        for (Telefono telefono : telefonos) {
            telefono.setUsuario(nuevoUsuario);
            nuevoUsuario.getTelefonos().add(telefono);
        }

        nuevoUsuario = usuarioRepository.save(nuevoUsuario);


        User newUser =new User();

        newUser.setId(nuevoUsuario.getUserId());
        newUser.setNombre(nuevoUsuario.getNombre());
        newUser.setCorreo(nuevoUsuario.getEmail());
        newUser.setPassword(nuevoUsuario.getPassword());
        newUser.setToken(nuevoUsuario.getToken());

        List<UserTelefonosInner> newUsertelefonos = this.getUserTelefonos(nuevoUsuario.getTelefonos());        
        newUser.setTelefonos(newUsertelefonos);

        newUser.setActivo(nuevoUsuario.getActivo());
        newUser.setCreado(nuevoUsuario.getCreado());
        newUser.setModificado(nuevoUsuario.getModificado());
        newUser.setUltimoLogin(nuevoUsuario.getUltimoLogin());

        ResponseEntity<User> response = new ResponseEntity<>(newUser, HttpStatus.OK);
        return response;
        //return UserApiDelegate.super.addUser(user);
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID userId) {

        if (logger.isDebugEnabled()) {
            logger.debug("UserServiceImpl delete User");
        }

        if (logger.isInfoEnabled()) {
            logger.info(userId.toString());
        }

        Usuario usuario = new Usuario();
        usuario.setUserId(userId);
        usuarioRepository.delete(usuario);

        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<User> getUserById(UUID userId) {
        if (logger.isDebugEnabled()) {
            logger.debug("UserServiceImpl obtiene un usuario por id");
        }

        if (logger.isInfoEnabled()) {
            logger.info(userId.toString());
        }

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(userId);

        if (!usuarioEncontrado.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario " + userId + " no encontrado");
        }

        User user = new User();
        user.setId(usuarioEncontrado.get().getUserId());
        user.setNombre(usuarioEncontrado.get().getNombre());
        user.setCorreo(usuarioEncontrado.get().getEmail());
        user.setPassword(usuarioEncontrado.get().getPassword());
        user.setActivo(usuarioEncontrado.get().getActivo());

        List<UserTelefonosInner> newUsertelefonos = this.getUserTelefonos(usuarioEncontrado.get().getTelefonos());        
        user.setTelefonos(newUsertelefonos);

        user.setToken(usuarioEncontrado.get().getToken());
        user.creado(usuarioEncontrado.get().getCreado());
        user.setModificado(usuarioEncontrado.get().getModificado());
        user.setUltimoLogin(usuarioEncontrado.get().getUltimoLogin());

        ResponseEntity<User> response = new ResponseEntity<>(user, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<List<User>> listUsers() {
        
        if (logger.isDebugEnabled()) {
            logger.debug("UserServiceImpl List Users");
        }

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<User> users = new ArrayList<>();

        if (!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                
                User user = new User();
                user.setId(usuario.getUserId());
                user.setNombre(usuario.getNombre());
                user.setCorreo(usuario.getEmail());

                List<UserTelefonosInner> newUsertelefonos = this.getUserTelefonos(usuario.getTelefonos());        
                user.setTelefonos(newUsertelefonos);

                user.setToken(usuario.getToken());
                user.setActivo(usuario.getActivo());
                user.setCreado(usuario.getCreado());
                user.setModificado(usuario.getModificado());
                user.setUltimoLogin(usuario.getUltimoLogin());
                
                users.add(user);


            }
        }

        ResponseEntity<List<User>> response = new ResponseEntity<>(users, HttpStatus.OK);

        return response;
    }

    @Override
    public ResponseEntity<Void> updateUser(UUID userId, User user) {

        if (logger.isDebugEnabled()) {
            logger.debug("UserServiceImpl Add User");
        }

        if (logger.isInfoEnabled()) {
            logger.info(user.toString());
            logger.info("emailValidationPattern["+this.emailValidationPattern+"]");
        }

        if (user.getCorreo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo no puede ser nulo");
        }

        Matcher matcher = emailPATTERN.matcher(user.getCorreo());
        if (!matcher.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo no valido " + user.getCorreo());
        }

//        if (usuarioRepository.existsByEmail(user.getCorreo())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo ya existe");
//        }

        if (user.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password no puede ser nula");
        }

        Matcher passwordMatcher = passwordPATTERN.matcher(user.getPassword());
        if (!passwordMatcher.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password no valida");
        }

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(userId);

        if (!usuarioEncontrado.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario " + userId + " no encontrado");
        }

        usuarioEncontrado.get().setNombre(user.getNombre());
        usuarioEncontrado.get().setPassword(user.getPassword());
        usuarioEncontrado.get().setActivo(user.getActivo());
        usuarioEncontrado.get().setModificado(OffsetDateTime.now());

        usuarioEncontrado.get().getTelefonos().clear();
        List<Telefono> telefonos = this.getTelefonos(user.getTelefonos());
        for (Telefono telefono : telefonos) {
            telefono.setUsuario(usuarioEncontrado.get());
            usuarioEncontrado.get().getTelefonos().add(telefono);
        }

        //usuarioEncontrado.get().setTelefonos(telefonos);

        usuarioRepository.save(usuarioEncontrado.get());

        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
        return response;      
    }

    private String generateToken(User usuario) {
        Collection<? extends GrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_USER")
                );

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
            usuario.getCorreo(),
            usuario.getPassword(),
            authorities         
        );

        String token = jwtTokenUtil.generateToken(userDetails);

        return token;
    }

    private List<Telefono> getTelefonos(List<UserTelefonosInner> userTelefonosInner) {

        List<Telefono> telefonos = new ArrayList<>();
        
        if (userTelefonosInner != null && !userTelefonosInner.isEmpty()) {
            for ( UserTelefonosInner newPhone : userTelefonosInner ) {
                Telefono telefonoNuevo = new Telefono();
                
                telefonoNuevo.setNumero(newPhone.getNumero());
                telefonoNuevo.setCitycode(newPhone.getCodigoCiudad());
                telefonoNuevo.setContryCode(newPhone.getCodigoPais());

                telefonos.add(telefonoNuevo);
            }
        }

        return telefonos;
    }

    private List<UserTelefonosInner> getUserTelefonos(List<Telefono> telefonos) {

        List<UserTelefonosInner> userTelefonos = new ArrayList<>();
        
        if (telefonos != null && !telefonos.isEmpty()) {
            for ( Telefono telefono : telefonos ) {
                UserTelefonosInner newPhone = new UserTelefonosInner();

                newPhone.setNumero(telefono.getNumero());
                newPhone.setCodigoCiudad(telefono.getCitycode());
                newPhone.setCodigoPais(telefono.getContryCode());

                userTelefonos.add(newPhone);
            }
        }

        return userTelefonos;
    }

}
