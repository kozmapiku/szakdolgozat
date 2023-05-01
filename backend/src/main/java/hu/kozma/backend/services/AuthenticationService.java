package hu.kozma.backend.services;

import hu.kozma.backend.dto.LoginDTO;
import hu.kozma.backend.exceptions.UsernameAlreadyTakenException;
import hu.kozma.backend.exceptions.WrongPasswordException;
import hu.kozma.backend.model.Role;
import hu.kozma.backend.model.User;
import hu.kozma.backend.repository.UserRepository;
import hu.kozma.backend.rest.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public void register(User user) throws UsernameAlreadyTakenException {
        checkUserValidation(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public LoginDTO login(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        user = userRepository.findUserByEmail(user.getEmail()).orElseThrow();
        String jwtToken = jwtTokenUtil.generateToken(user);
        return new LoginDTO(user, jwtToken, System.currentTimeMillis() + JwtTokenUtil.JWT_TOKEN_VALIDITY * 1000);
    }

    public LoginDTO getUser(Principal principal) {
        User user = userRepository.findUserByEmail(principal.getName()).orElseThrow();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(user.getEmail());
        loginDTO.setFirstName(user.getFirstName());
        loginDTO.setLastName(user.getLastName());
        return loginDTO;
    }

    private void checkUserValidation(User user) throws UsernameAlreadyTakenException {
        if (userRepository.existsUserByEmail(user.getEmail())) {
            throw new UsernameAlreadyTakenException("Ez az e-mail cím már foglalt!");
        }
    }

    public void update(User user, Principal principal, String newPassword) throws WrongPasswordException {
        User old = userRepository.findUserByEmail(principal.getName()).orElseThrow();
        if (!passwordEncoder.matches(user.getPassword(), old.getPassword())) {
            throw new WrongPasswordException("A megadott jelszó nem megfelelő!");
        }
        if (!old.getEmail().equals(user.getEmail())) {
            old.setEmail(user.getEmail());
        }
        if (!old.getFirstName().equals(user.getFirstName())) {
            old.setFirstName(user.getFirstName());
        }
        if (!old.getLastName().equals(user.getLastName())) {
            old.setLastName(user.getLastName());
        }
        if (newPassword != null) {
            old.setPassword(newPassword);
        }
        userRepository.save(old);
    }
}
