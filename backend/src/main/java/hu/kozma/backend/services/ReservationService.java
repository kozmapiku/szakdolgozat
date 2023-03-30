package hu.kozma.backend.services;

import hu.kozma.backend.model.Reservation;
import hu.kozma.backend.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

    public final ReservationRepository reservationRepository;

    public List<Reservation> getReservations(String email) {
        return reservationRepository.findAllByUserEmail(email);
    }
}
