package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.AccommodationDTO;
import hu.kozma.backend.model.Accommodation;

import java.util.stream.Collectors;

public class AccommodationMapper {

    public static Accommodation toAccommodation(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(accommodationDTO.getId());
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
        accommodationDTO.setFloor(accommodation.getFloor());
        accommodationDTO.setDoor(accommodation.getDoor());
        accommodationDTO.setLat(accommodation.getLat());
        accommodationDTO.setLng(accommodation.getLng());
        accommodationDTO.setDescription(accommodation.getDescription());
        accommodationDTO.setMaxGuests(accommodation.getMaxGuests());
        accommodationDTO.setMainImageIndex(accommodation.getMainImage().getIndex());
        accommodationDTO.setOwner(accommodation.getUser().getEmail());
        return accommodationDTO;
    }

    public static AccommodationDTO toAccommodationDetailsDTO(Accommodation accommodation) {
        AccommodationDTO accommodationDTO = toAccommodationDTO(accommodation);
        accommodationDTO.setAnnounces(accommodation.getAnnounces().stream()
                .map(AnnounceDateMapper::toAnnounceDateDTO)
                .collect(Collectors.toList()));
        accommodationDTO.setReviews(accommodation.getReviews().stream()
                .map(ReviewMapper::toReviewDTO)
                .toList());
        return accommodationDTO;
    }
}
