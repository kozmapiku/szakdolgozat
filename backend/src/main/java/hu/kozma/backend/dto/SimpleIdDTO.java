package hu.kozma.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class SimpleIdDTO {
	@NotNull
	private Long id;
}
