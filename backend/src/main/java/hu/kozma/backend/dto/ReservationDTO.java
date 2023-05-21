package hu.kozma.backend.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ReservationDTO {
    private Long from;
    private Long end;
    private Long id;
    private Integer guests;
    private Double price;
    private AccommodationDTO accommodation;
    private String mainImageLocation;
    private CompactReviewDTO review;
}
