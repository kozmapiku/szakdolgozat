package hu.kozma.backend.services;

import hu.kozma.backend.dto.LoginDTO;
import hu.kozma.backend.dto.UserDTO;
import hu.kozma.backend.exceptions.UsernameAlreadyTakenException;
import hu.kozma.backend.exceptions.WrongPasswordException;
import hu.kozma.backend.mappers.UserMapper;
import hu.kozma.backend.model.Role;
import hu.kozma.backend.model.User;
import hu.kozma.backend.repository.UserRepository;
import hu.kozma.backend.rest.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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

    public void register(UserDTO userDTO) throws UsernameAlreadyTakenException {
        User user = UserMapper.toUser(userDTO);
        checkUserValidation(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public LoginDTO login(UserDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        User user = userRepository.findUserByEmail(userDTO.getEmail()).orElseThrow();
        String jwtToken = jwtTokenUtil.generateToken(user);
        return new LoginDTO(user, jwtToken, System.currentTimeMillis() + JwtTokenUtil.JWT_TOKEN_VALIDITY * 1000);
    }

    public LoginDTO getUser(String email) {
        return userRepository.findUserByEmail(email)
                .map(UserMapper::toLoginDTO)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void updateUser(UserDTO user, String email, String newPassword) throws WrongPasswordException {
        User old = userRepository.findUserByEmail(email).orElseThrow();
        if (!passwordEncoder.matches(user.getPassword(), old.getPassword())) {
            throw new WrongPasswordException("A megadott jelszó nem megfelelő!");
        }
        old.setEmail(user.getEmail());
        old.setFirstName(user.getFirstName());
        old.setLastName(user.getLastName());
        if (newPassword != null) {
            old.setPassword(newPassword);
        }
        userRepository.save(old);
    }

    private void checkUserValidation(User user) throws UsernameAlreadyTakenException {
        if (userRepository.existsUserByEmail(user.getEmail())) {
            throw new UsernameAlreadyTakenException("Ez az e-mail cím már foglalt!");
        }
    }
}
