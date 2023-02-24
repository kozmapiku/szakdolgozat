package hu.kozma.backend.dto;

import hu.kozma.backend.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class LoginDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private Long expiresIn;
    public LoginDTO(User user, String token, Long expiresIn) {
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        this.expiresIn = expiresIn;
        this.token = token;
    }
}
