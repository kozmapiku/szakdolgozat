package hu.kozma.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SaveAccommodationDTO {
    @NotBlank
    @Size(min = 5, max = 15, message = "A név hossza nem megfelelő.")
    private String name;
    @NotBlank
    @Size(max = 100, message = "A cím hossza nem megfelelő.")
    private String address;
    @Size(min = -10, max = 200, message = "Az emelet nem megfelelő.")
    private Integer floor;
    @Size(max = 200, message = "Az ajtó nem megfelelő.")
    private Integer door;
    @NotNull
    private Float lat;
    @NotNull
    private Float lng;
    @NotBlank
    @Size(min = 100, max = 2500, message = "A leírás hossza nem megfelelő.")
    private String description;
    @NotNull
    @Size(min = 1, max = 100, message = "A maximális befogadó képesség nem megfelelő.")
    private Integer maxGuests;
    @NotNull
    @Size(min = 1, max = 100, message = "A meghírdetett időpontok nem megfelelőek.")
    private List<AnnounceDateDTO> announces;
}
