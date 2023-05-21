package hu.kozma.backend.dto;

import hu.kozma.backend.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @Email(message = "Az e-mail formátuma nem megfelelő.")
    private String email;
    @Size(min = 8, message = "A jelszó nem elég hosszú.")
    private String password;
}
