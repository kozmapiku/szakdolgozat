package hu.kozma.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
	@NotBlank(message = "Az e-mail nem lehet üres.")
	@Email(message = "Az e-mail formátuma nem megfelelő.")
	private String email;
	@Size(min = 8, message = "A jelszó nem elég hosszú.")
	private String password;
}
