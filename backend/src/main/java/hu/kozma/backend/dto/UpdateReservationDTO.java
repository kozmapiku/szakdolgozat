package hu.kozma.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReservationDTO {
	@NotNull(message = "A foglalás azonosítója nem lehet üres.")
	private Long id;
	@NotNull(message = "A vendégek száma nem lehet üres.")
	@Min(value = 1, message = "A vendégek száma túl kevés.")
	private Integer guests;
}
