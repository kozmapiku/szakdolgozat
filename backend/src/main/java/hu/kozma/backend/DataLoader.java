package hu.kozma.backend;

import hu.kozma.backend.models.Role;
import hu.kozma.backend.models.User;
import hu.kozma.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        Role role = new Role();
        role.setName("user");
        user.addRole(role);
        userRepository.save(user);
    }
}
