package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.LoginDTO;
import hu.kozma.backend.dto.UserDTO;
import hu.kozma.backend.mappers.UserMapper;
import hu.kozma.backend.model.User;
import hu.kozma.backend.repository.UserRepository;
import hu.kozma.backend.rest.JwtTokenUtil;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class  AuthController {
    private final AuthenticationService authenticationService;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        System.out.println("ASD");
        System.out.println(user);
        return user;
    }

    @SneakyThrows
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        User user = UserMapper.toUser(userDTO);
        authenticationService.register(user);
        return RestResponseHandler.generateResponse("A regisztráció sikeres volt!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateWithJwt(@RequestBody UserDTO userDTO){
        User user = UserMapper.toUser(userDTO);
        LoginDTO loginDTO = authenticationService.login(user);
        return RestResponseHandler.generateResponse(loginDTO);
    }
}
