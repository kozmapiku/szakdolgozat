package hu.kozma.backend.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchAccommodationDTO {
    private String name;
    private String address;
    @Nullable
    @Size(min = 1, message = "A vendégek száma nem megfelelő.")
    private Integer guests;
    private Long fromDate;
    private Long endDate;
    private Boolean showOwn;
}
