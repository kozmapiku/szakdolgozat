package hu.kozma.backend.dto;

import hu.kozma.backend.models.City;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class AccommodationDTO {
    @NonNull
    private City city;
    @NonNull
    private int maxGuests;
    @NonNull
    private String address;
}
