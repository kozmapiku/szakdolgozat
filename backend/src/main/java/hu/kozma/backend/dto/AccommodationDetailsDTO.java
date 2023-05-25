package hu.kozma.backend.dto;

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
public class AccommodationDetailsDTO extends AccommodationDTO {
	private List<CompactReviewDTO> reviews;
	private List<byte[]> images;
	private List<AnnounceDateDTO> announces;
	private String owner;
	private String description;
	private List<Long> reservedDays;

	public AccommodationDetailsDTO(AccommodationDTO accommodationDTO) {
		super(accommodationDTO.getId(), accommodationDTO.getName(), accommodationDTO.getMaxGuests(),
				accommodationDTO.getAddress(), accommodationDTO.getLat(), accommodationDTO.getLng(),
				accommodationDTO.getMainImage(), accommodationDTO.getStar());
	}
}
