package hu.kozma.backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompactReviewDTO {
	private String comment;
	private Integer star;
	private String name;
}
