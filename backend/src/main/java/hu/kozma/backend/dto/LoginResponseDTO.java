package hu.kozma.backend.dto;

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
public class LoginResponseDTO extends UserDTO {
	private String token;
	private Long expiresIn;

	public LoginResponseDTO(UserDTO userDTO, String token, Long expiresIn) {
		super(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail());
		this.token = token;
		this.expiresIn = expiresIn;
	}
}
