package hu.kozma.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AccommodationDTO {
    private long id;
    @NonNull
    private String name;
    @NonNull
    private String city;
    @NonNull
    @JsonProperty("max_guests")
    private Integer maxGuest;
    @NonNull
    private String address;
    @NonNull
    private List<AnnounceDateDto> announceDateList;
    @NonNull
    private List<byte[]> listOfImages;
    private byte[] mainImage;
}
