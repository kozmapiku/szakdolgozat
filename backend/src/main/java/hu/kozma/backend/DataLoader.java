package hu.kozma.backend;

import hu.kozma.backend.model.Role;
import hu.kozma.backend.model.User;
import hu.kozma.backend.repository.UserRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Base64;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public void run(ApplicationArguments args) {
        User user = new User();
        user.setEmail("teszt@gmail.com");
        user.setFirstName("Elek");
        user.setLastName("Teszt");
        user.setPassword(passwordEncoder.encode("teszt"));
        user.setRole(Role.USER);
        user.setEnabled(true);
        userRepository.save(user);

        SecretKey secretKey = secretKeyFor(SignatureAlgorithm.HS512);
        String a = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println(a);
    }
}
