package hu.kozma.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
	@Email(message = "Az e-mail formátuma nem megfelelő.")
	private String email;
	@NotBlank(message = "A keresztnév nem lehet üres.")
	private String firstName;
	@NotBlank(message = "A vezetéknév nem lehet üres.")
	private String lastName;
	@NotBlank
	@Size(min = 8, message = "A jelszó hossza nem megfelelő.")
	private String password;
}
