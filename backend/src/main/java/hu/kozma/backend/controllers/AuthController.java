package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.LoginDTO;
import hu.kozma.backend.dto.RegisterDTO;
import hu.kozma.backend.dto.UpdateUserDTO;
import hu.kozma.backend.dto.UserDTO;
import hu.kozma.backend.exceptions.UsernameAlreadyTakenException;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

	private final AuthenticationService authenticationService;

	@RequestMapping("/user")
	public ResponseEntity<?> getUser(Principal user) {
		UserDTO userDTO = authenticationService.getUser(user.getName());
		return RestResponseHandler.generateResponse(userDTO);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) throws UsernameAlreadyTakenException {
		authenticationService.register(registerDTO);
		return RestResponseHandler.generateResponse("A regisztráció sikeres volt!");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
		UserDTO userDTO = authenticationService.login(loginDTO);
		return RestResponseHandler.generateResponse(userDTO);
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserDTO userDTO, Principal principal) {
		authenticationService.updateUser(userDTO, principal.getName(), userDTO.getNewPassword());
		return RestResponseHandler.generateResponse("Felhasználói adatok megváltoztatása sikeres!");
	}
}
