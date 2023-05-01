package hu.kozma.backend.services;

import hu.kozma.backend.model.Reservation;
import hu.kozma.backend.repository.AccommodationRepository;
import hu.kozma.backend.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

    public final ReservationRepository reservationRepository;
    private final AccommodationRepository accommodationRepository;

    public List<Reservation> getReservations(String email) {
        return reservationRepository.findAllByUserEmail(email);
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id).orElseThrow();
    }

    public void deleteReservation(Long id, String name) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        if (!reservation.getUser().getEmail().equals(name)) {
            throw new IllegalArgumentException();
        }
        reservationRepository.delete(reservation);
    }
}
