package hu.kozma.backend.dto;

import jakarta.validation.constraints.Max;
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
	@Max(value = 5, message = "Az értékelés nem megfelelő.")
	private Integer star;
	@NotNull(message = "A foglalás száma kötelező.")
	private Long reservationId;
	@NotNull(message = "A szállás száma kötelező.")
	private Long accommodationId;
}
