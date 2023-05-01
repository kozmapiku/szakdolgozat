package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.LoginDTO;
import hu.kozma.backend.dto.UserDTO;
import hu.kozma.backend.exceptions.WrongPasswordException;
import hu.kozma.backend.mappers.UserMapper;
import hu.kozma.backend.model.User;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class  AuthController {
    private final AuthenticationService authenticationService;

    @RequestMapping("/user")
    public LoginDTO user(Principal user) {
        return authenticationService.getUser(user);
    }

    @SneakyThrows
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        User user = UserMapper.toUser(userDTO);
        authenticationService.register(user);
        return RestResponseHandler.generateResponse("A regisztráció sikeres volt!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateWithJwt(@RequestBody UserDTO userDTO) {
        User user = UserMapper.toUser(userDTO);
        LoginDTO loginDTO = authenticationService.login(user);
        return RestResponseHandler.generateResponse(loginDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO, Principal principal) throws WrongPasswordException {
        User user = UserMapper.toUser(userDTO);
        authenticationService.update(user, principal, userDTO.getNewPassword());
        return RestResponseHandler.generateResponse("Felhasználói adatok megváltoztatása sikeres!");
    }
}
