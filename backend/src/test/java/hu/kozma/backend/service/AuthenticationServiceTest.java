package hu.kozma.backend.service;

import hu.kozma.backend.dto.LoginDTO;
import hu.kozma.backend.dto.UserDTO;
import hu.kozma.backend.exceptions.UsernameAlreadyTakenException;
import hu.kozma.backend.repository.AccommodationRepository;
import hu.kozma.backend.repository.UserRepository;
import hu.kozma.backend.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import static hu.kozma.backend.model.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationServiceTest {

	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccommodationRepository accommodationRepository;

	@BeforeEach
	public void setup() {
		userRepository.deleteAll();
	}

	@Test
	public void registerTestSuccess() throws UsernameAlreadyTakenException {
		authenticationService.register(registerDTO());
		assertEquals(1, userRepository.findAll().size());
	}

	@Test
	public void registerEmailAlreadyTaken() throws UsernameAlreadyTakenException {
		authenticationService.register(registerDTO());
		assertThrows(UsernameAlreadyTakenException.class, () -> authenticationService.register(registerDTO()));
		assertEquals(1, userRepository.findAll().size());
	}

	@Test
	public void loginUserSuccess() throws UsernameAlreadyTakenException {
		authenticationService.register(registerDTO());
		assertEquals(1, userRepository.findAll().size());
		UserDTO userDTO = authenticationService.login(loginDTO());
		assertNotNull(userDTO);
	}

	@Test
	public void loginPasswordIncorrect() throws UsernameAlreadyTakenException {
		authenticationService.register(registerDTO());
		assertEquals(1, userRepository.findAll().size());
		LoginDTO loginDTO = LoginDTO.builder().email(email()).password(password2()).build();
		assertThrows(BadCredentialsException.class, () -> authenticationService.login(loginDTO));
	}

	@Test
	public void loginEmailIncorrect() throws UsernameAlreadyTakenException {
		authenticationService.register(registerDTO());
		assertEquals(1, userRepository.findAll().size());
		LoginDTO loginDTO = LoginDTO.builder().email("teszt").password(password()).build();
		assertThrows(InternalAuthenticationServiceException.class, () -> authenticationService.login(loginDTO));
	}
}
