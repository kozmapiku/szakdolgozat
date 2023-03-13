package hu.kozma.backend.dto;

import hu.kozma.backend.model.User;
import lombok.Getter;
import lombok.Setter;

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
