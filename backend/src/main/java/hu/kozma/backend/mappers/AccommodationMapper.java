package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.AccommodationAltDTO;
import hu.kozma.backend.dto.AccommodationDTO;
import hu.kozma.backend.dto.AccommodationDetailsDTO;
import hu.kozma.backend.dto.SaveAccommodationDTO;
import hu.kozma.backend.model.Accommodation;
import hu.kozma.backend.model.Review;

import static hu.kozma.backend.repository.FileSystemRepository.getImage;

public class AccommodationMapper {

	public static Accommodation toAccommodation(SaveAccommodationDTO accommodationDTO) {
		Accommodation accommodation = new Accommodation();
		accommodation.setName(accommodationDTO.getName());
		accommodation.setAddress(accommodationDTO.getAddress());
		accommodation.setFloor(accommodationDTO.getFloor());
		accommodation.setDoor(accommodationDTO.getDoor());
		accommodation.setLat(accommodationDTO.getLat());
		accommodation.setLng(accommodationDTO.getLng());
		accommodation.setDescription(accommodationDTO.getDescription());
		accommodation.setMaxGuests(accommodationDTO.getMaxGuests());
		accommodationDTO.getAnnounces().stream()
				.map(AnnounceDateMapper::toAnnounceDate).forEach(accommodation::addAnnounceDate);
		return accommodation;
	}

	public static AccommodationDTO toAccommodationDTO(Accommodation accommodation) {
		AccommodationDTO accommodationDTO = new AccommodationDTO();
		accommodationDTO.setId(accommodation.getId());
		accommodationDTO.setName(accommodation.getName());
		accommodationDTO.setAddress(accommodation.getAddress());
		accommodationDTO.setLat(accommodation.getLat());
		accommodationDTO.setLng(accommodation.getLng());
		accommodationDTO.setMaxGuests(accommodation.getMaxGuests());
		accommodationDTO.setStar(accommodation.getReviews().stream().mapToDouble(Review::getStar).average().orElse(0.0));
		return accommodationDTO;
	}

	public static AccommodationDetailsDTO toAccommodationDetailsDTO(Accommodation accommodation) {
		AccommodationDetailsDTO accommodationDetailsDTO = new AccommodationDetailsDTO(toAccommodationDTO(accommodation));
		accommodationDetailsDTO.setOwner(accommodation.getUser().getEmail());
		accommodationDetailsDTO.setDescription(accommodation.getDescription());
		accommodationDetailsDTO.setAnnounces(accommodation.getAnnounces().stream().map(AnnounceDateMapper::toAnnounceDateDTO).toList());
		accommodationDetailsDTO.setReviews(accommodation.getReviews().stream().map(ReviewMapper::toCompactReviewDTO).toList());
		return accommodationDetailsDTO;
	}

	public static AccommodationAltDTO toAccommodationAltDTO(Accommodation accommodation) {
		AccommodationAltDTO accommodationAltDTO = new AccommodationAltDTO();
		accommodationAltDTO.setName(accommodation.getName());
		accommodationAltDTO.setAddress(accommodation.getAddress());
		accommodationAltDTO.setId(accommodation.getId());
		accommodationAltDTO.setMainImage(getImage(accommodation.getMainImage()));
		accommodationAltDTO.setMaxGuests(accommodation.getMaxGuests());
		return accommodationAltDTO;
	}
}
