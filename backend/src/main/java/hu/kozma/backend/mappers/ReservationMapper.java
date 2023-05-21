package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.ReservationDTO;
import hu.kozma.backend.model.Reservation;

public class ReservationMapper {
    public static Reservation toReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setStartDate(MapperUtils.toDate(reservationDTO.getFrom()));
        reservation.setEndDate(MapperUtils.toDate(reservationDTO.getEnd()));
        reservation.setGuestNumber(reservationDTO.getGuests());
        return reservation;
    }

    public static ReservationDTO toReservationDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setGuests(reservation.getGuestNumber());
        reservationDTO.setFrom(MapperUtils.toLongDate(reservation.getStartDate()));
        reservationDTO.setEnd(MapperUtils.toLongDate(reservation.getEndDate()));
        reservationDTO.setPrice(reservation.getPrice());
        if (reservation.getReview() != null) {
            reservationDTO.setReview(ReviewMapper.toCompactReviewDTO(reservation.getReview()));
        }
        reservationDTO.setAccommodation(AccommodationMapper.toAccommodationDTO(reservation.getAccommodation()));
        reservationDTO.setMainImageLocation(reservation.getAccommodation().getMainImage().getLocation());
        return reservationDTO;
    }
}
