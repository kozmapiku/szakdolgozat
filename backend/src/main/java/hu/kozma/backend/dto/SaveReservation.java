package hu.kozma.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveReservation {
	@NotNull(message = "A szállás azonosítója nem lehet üres.")
	private Long accommodationId;
	@NotNull(message = "A kezdő dátum nem lehet üres.")
	private Long startDate;
	@NotNull(message = "A befejező dátum nem lehet üres.")
	private Long endDate;
	@NotNull(message = "A vendégek száma nem lehet üres.")
	@Min(value = 1, message = "A vendégek száma túl kevés.")
	private Integer guests;
}
