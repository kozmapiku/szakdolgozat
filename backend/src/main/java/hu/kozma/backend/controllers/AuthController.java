package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.LoginDTO;
import hu.kozma.backend.dto.UserDTO;
import hu.kozma.backend.exceptions.UsernameAlreadyTakenException;
import hu.kozma.backend.exceptions.WrongPasswordException;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class  AuthController {

    private final AuthenticationService authenticationService;

    @RequestMapping("/user")
    public ResponseEntity<?> getUser(Principal user) {
        LoginDTO loginDTO = authenticationService.getUser(user.getName());
        return RestResponseHandler.generateResponse(loginDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) throws UsernameAlreadyTakenException {
        authenticationService.register(userDTO);
        return RestResponseHandler.generateResponse("A regisztráció sikeres volt!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        LoginDTO loginDTO = authenticationService.login(userDTO);
        return RestResponseHandler.generateResponse(loginDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, Principal principal) throws WrongPasswordException {
        authenticationService.updateUser(userDTO, principal.getName(), userDTO.getNewPassword());
        return RestResponseHandler.generateResponse("Felhasználói adatok megváltoztatása sikeres!");
    }
}
