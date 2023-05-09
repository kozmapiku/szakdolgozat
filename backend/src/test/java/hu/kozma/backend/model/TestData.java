package hu.kozma.backend.model;

import hu.kozma.backend.dto.AccommodationDTO;
import hu.kozma.backend.dto.AnnounceDateDTO;

import java.util.List;

public class TestData {

    public static String accommodationName() {
        return "Accommodation name";
    }

    public static Integer maxGuests() {
        return 0;
    }

    public static String address() {
        return "address";
    }

    public static Float lat() {
        return 1.0f;
    }

    public static Float lng() {
        return 2.0f;
    }

    public static Long from() {
        return 3L;
    }

    public static Long end() {
        return 4L;
    }

    public static String description() {
        return "Accommodation description";
    }

    public static byte[] file() {
        return "file".getBytes();
    }

    public static AccommodationDTO minimumAccommodationDTO() {
        return AccommodationDTO.builder()
                .name(accommodationName())
                .maxGuests(maxGuests())
                .address(address())
                .lat(lat())
                .lng(lng())
                .description(description())
                .announces(List.of(minimumAnnounceDateDTO()))
                .images(List.of(file())).build();
    }

    public static AnnounceDateDTO minimumAnnounceDateDTO() {
        return AnnounceDateDTO.builder()
                .from(from())
                .end(end())
                .build();
    }

    public Accommodation minimumAccommodation() {
        return Accommodation.builder().build();
    }
}
