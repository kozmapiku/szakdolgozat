package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.AccommodationDTO;
import hu.kozma.backend.model.Accommodation;

public class AccommodationMapper {

    public static Accommodation toAccommodation(AccommodationDTO accommodationDTO) {
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
}
