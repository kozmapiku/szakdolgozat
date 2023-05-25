package hu.kozma.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnounceDateDTO {
	@NotNull(message = "A kezdő dátum nem lehet üres.")
	private Long startDate;
	@NotNull(message = "A befejező dátum nem lehet üres.")
	private Long endDate;
	@NotNull
	@Size(min = 1, max = 1000000, message = "Az ár nem megfelelő.")
	private Double price;
}
