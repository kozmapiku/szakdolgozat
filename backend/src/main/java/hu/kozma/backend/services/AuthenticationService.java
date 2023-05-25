package hu.kozma.backend.services;

import hu.kozma.backend.dto.*;
import hu.kozma.backend.exceptions.UsernameAlreadyTakenException;
import hu.kozma.backend.mappers.UserMapper;
import hu.kozma.backend.model.User;
import hu.kozma.backend.repository.UserRepository;
import hu.kozma.backend.rest.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;

	public void register(RegisterDTO userDTO) throws UsernameAlreadyTakenException {
		User user = UserMapper.toUser(userDTO);
		checkUserValidation(user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public UserDTO login(LoginDTO userDTO) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
		User user = userRepository.findUserByEmail(userDTO.getEmail()).orElseThrow(EntityNotFoundException::new);
		String jwtToken = jwtTokenUtil.generateToken(user);
		return new LoginResponseDTO(UserMapper.toUserDTO(user), jwtToken, System.currentTimeMillis() + JwtTokenUtil.JWT_TOKEN_VALIDITY);
	}

	public UserDTO getUser(String email) {
		return userRepository.findUserByEmail(email)
				.map(UserMapper::toUserDTO)
				.orElseThrow(IllegalArgumentException::new);
	}

	public void updateUser(UpdateUserDTO user, String email, String newPassword) {
		User oldUser = userRepository.findUserByEmail(email).orElseThrow(EntityNotFoundException::new);

		checkPasswordValidation(user.getPassword(), oldUser.getPassword());

		oldUser.setEmail(user.getEmail());
		oldUser.setFirstName(user.getFirstName());
		oldUser.setLastName(user.getLastName());
		if (newPassword != null) {
			oldUser.setPassword(passwordEncoder.encode(newPassword));
		}
		userRepository.save(oldUser);
	}

	private void checkPasswordValidation(String password, String password1) {
		if (!passwordEncoder.matches(password, password1)) {
			throw new BadCredentialsException("A megadott jelszó nem megfelelő!");
		}
	}

	private void checkUserValidation(User user) throws UsernameAlreadyTakenException {
		if (userRepository.existsUserByEmail(user.getEmail())) {
			throw new UsernameAlreadyTakenException("Ez az e-mail cím már foglalt!");
		}
	}
}
