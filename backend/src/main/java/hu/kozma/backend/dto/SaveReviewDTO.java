package hu.kozma.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveReviewDTO {
	@Size(max = 300, message = "A komment túl hosszú.")
	private String comment;
	@NotNull
	@Min(value = 1, message = "Az értékelésnek legalább 1-nek kell lennie.")
	@Max(value = 5, message = "Az értékelés maximum 5 lehet.")
	private Integer star;
	@NotNull(message = "A foglalás azonosítója kötelező.")
	private Long reservationId;
	@NotNull(message = "A szállás azonosítója kötelező.")
	private Long accommodationId;
}
