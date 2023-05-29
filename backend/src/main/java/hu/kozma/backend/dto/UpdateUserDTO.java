package hu.kozma.backend.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO extends RegisterDTO {
	@Size(min = 8, message = "Az új jelszú túl rövid.")
	private String newPassword;
}
