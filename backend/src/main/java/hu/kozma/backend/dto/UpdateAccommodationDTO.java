package hu.kozma.backend.dto;

import jakarta.validation.constraints.NotNull;
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
public class UpdateAccommodationDTO extends SaveAccommodationDTO {
	@NotNull(message = "A szállás azonosítója nem lehet üres.")
	private Long id;
}
