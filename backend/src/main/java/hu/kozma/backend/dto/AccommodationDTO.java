package hu.kozma.backend.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AccommodationDTO {
    private long id;
    @NonNull
    private String name;
    @NonNull
    private Integer maxGuests;
    @NonNull
    private String address;
    @Nullable
    private Integer floor;
    @Nullable
    private Integer door;
    @NonNull
    private Float lat;
    @NonNull
    private Float lng;
    @NonNull
    private String description;
    @NonNull
    @Size(min = 1)
    private List<AnnounceDateDTO> announces;
    @Nullable
    private List<Long> reservedDays;
    @NonNull
    @Size(min = 1)
    private List<byte[]> images;
    @Nullable
    private Integer mainImageIndex;
    private byte[] mainImage;
    private String owner;
    @Nullable
    private List<ReviewDTO> reviews;
}
