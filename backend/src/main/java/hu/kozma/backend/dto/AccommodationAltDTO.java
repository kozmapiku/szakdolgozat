package hu.kozma.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationAltDTO {
    private String name;
    private List<byte[]> images;
    private byte[] mainImage;
    private String address;
    private Long id;
    private Integer maxGuests;
}
