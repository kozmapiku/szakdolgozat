package hu.kozma.backend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SaveAccommodationDTO {
	@NotBlank
	@Size(min = 5, max = 30, message = "A név hossza nem megfelelő.")
	private String name;
	@NotBlank
	@Size(max = 100, message = "A cím hossza nem megfelelő.")
	private String address;
	@Min(value = -10, message = "Az emelet száma túl kicsi.")
	@Max(value = 200, message = "Az emelet száma túl nagy.")
	private Integer floor;
	@Min(value = 0, message = "Az ajtó száma túl kicsi.")
	@Max(value = 200, message = "Az ajtó száma túl nagy.")
	private Integer door;
	@NotNull
	private Float lat;
	@NotNull
	private Float lng;
	@NotBlank
	@Size(min = 100, max = 2500, message = "A leírás hossza nem megfelelő.")
	private String description;
	@NotNull
	@Min(value = 1, message = "A maximális befogadó képesség túl kevés.")
	@Max(value = 100, message = "A maximális befogadó képesség túl sok.")
	private Integer maxGuests;
	@NotNull
	@Size(min = 1, max = 100, message = "A meghírdetett időpontok nem megfelelőek.")
	private List<AnnounceDateDTO> announces;
}
