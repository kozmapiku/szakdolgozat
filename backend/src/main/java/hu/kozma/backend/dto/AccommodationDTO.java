package hu.kozma.backend.dto;

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
    private int maxGuest;
    @NonNull
    private String address;
    @NonNull
    private List<AnnounceDateDto> announceDateList;
    @NonNull
    private List<ImageDTO> listOfImages;
    private ImageDTO mainImage;
}
