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
}
