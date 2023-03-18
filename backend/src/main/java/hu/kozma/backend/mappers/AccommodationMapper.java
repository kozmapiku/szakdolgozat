package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.AccommodationDTO;
import hu.kozma.backend.model.Accommodation;
import hu.kozma.backend.model.City;

public class AccommodationMapper {

    public static Accommodation toAccommodation(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = new Accommodation();
        accommodation.setName(accommodationDTO.getName());
        accommodation.setAddress(accommodationDTO.getAddress());
        accommodation.setCity(City.valueOf(accommodationDTO.getCity().toUpperCase()));
        accommodation.setMaxGuests(accommodationDTO.getMaxGuest());
        accommodationDTO.getAnnounceDateList().stream()
                .map(AnnounceDateMapper::toAnnounceDate).forEach(accommodation::addAnnounceDate);
        return accommodation;
    }

    public static AccommodationDTO toAccommodationDTO(Accommodation accommodation) {
        AccommodationDTO accommodationDTO = new AccommodationDTO();
        accommodationDTO.setId(accommodation.getId());
        accommodationDTO.setName(accommodation.getName());
        accommodationDTO.setCity(accommodation.getCity().name());
        accommodationDTO.setAddress(accommodation.getAddress());
        accommodationDTO.setMaxGuest(accommodation.getMaxGuests());
        return accommodationDTO;
    }
}
