package hu.kozma.backend.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ReservationDTO {
    private AccommodationAltDTO accommodation;
    private Long startDate;
    private Long endDate;
    private Long id;
    private Integer guests;
    private Double price;
    private Boolean isEnded;
    private CompactReviewDTO review;
}
