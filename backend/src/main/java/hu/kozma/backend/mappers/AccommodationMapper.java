package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.AccommodationDTO;
import hu.kozma.backend.models.Accommodation;

public class AccommodationMapper {
    public static Accommodation toAccommodation(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = new Accommodation();
        accommodation.setAddress(accommodationDTO.getAddress());
        accommodation.setCity(accommodationDTO.getCity());
        accommodation.setMaxGuests(accommodationDTO.getMaxGuests());
        return accommodation;
    }
}
