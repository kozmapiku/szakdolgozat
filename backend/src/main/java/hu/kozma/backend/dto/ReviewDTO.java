package hu.kozma.backend.dto;

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
public class ReviewDTO {
	private String comment;
	private Integer star;
	private Long reservationId;
	private AccommodationAltDTO accommodation;
}
