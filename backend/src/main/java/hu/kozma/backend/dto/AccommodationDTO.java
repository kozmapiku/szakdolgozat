package hu.kozma.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AccommodationDTO {
	private Long id;
	private String name;
	private Integer maxGuests;
	private String address;
	private Float lat;
	private Float lng;
	private byte[] mainImage;
	private Double star;
}
