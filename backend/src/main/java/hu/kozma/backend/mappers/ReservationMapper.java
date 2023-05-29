package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.ReservationDTO;
import hu.kozma.backend.dto.SaveReservationDTO;
import hu.kozma.backend.model.Reservation;

public class ReservationMapper {
	public static Reservation toReservation(SaveReservationDTO reservationDTO) {
		Reservation reservation = new Reservation();
		reservation.setStartDate(MapperUtils.toDate(reservationDTO.getStartDate()));
		reservation.setEndDate(MapperUtils.toDate(reservationDTO.getEndDate()));
		reservation.setGuestNumber(reservationDTO.getGuests());
		return reservation;
	}

	public static ReservationDTO toReservationDTO(Reservation reservation) {
		ReservationDTO reservationDTO = new ReservationDTO();
		reservationDTO.setId(reservation.getId());
		reservationDTO.setStartDate(MapperUtils.toLongDate(reservation.getStartDate()));
		reservationDTO.setEndDate(MapperUtils.toLongDate(reservation.getEndDate()));
		reservationDTO.setPrice(reservation.getPrice());
		reservationDTO.setGuests(reservation.getGuestNumber());
		reservationDTO.setAccommodation(AccommodationMapper.toAccommodationAltDTO(reservation.getAccommodation()));
		reservationDTO.setIsEnded(reservation.isExpired());
		if (reservation.getReview() != null)
			reservationDTO.setReview(ReviewMapper.toCompactReviewDTO(reservation.getReview()));
		return reservationDTO;
	}
}
