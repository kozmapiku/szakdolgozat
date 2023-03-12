package hu.kozma.backend.services;

import hu.kozma.backend.dto.LoginDTO;
import hu.kozma.backend.exceptions.UsernameAlreadyTakenException;
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
        return new LoginDTO(user, jwtToken, System.currentTimeMillis() + JwtTokenUtil.JWT_TOKEN_VALIDITY*1000);
    }

    private void checkUserValidation(User user) throws UsernameAlreadyTakenException {
        if(userRepository.existsUserByEmail(user.getEmail())) {
            throw new UsernameAlreadyTakenException("Ez az e-mail cím már foglalt!");
        }
    }
}
