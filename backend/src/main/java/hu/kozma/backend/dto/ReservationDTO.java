package hu.kozma.backend.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReservationDTO {
    @NonNull
    private Long from;
    @NonNull
    private Long end;
    @NonNull
    private Long id;
    @NonNull
    private Integer guests;
    private Double price;
    private AccommodationDTO accommodation;
    private String mainImageLocation;
    private ReviewDTO review;
}
