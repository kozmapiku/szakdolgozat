package hu.kozma.backend.repository;

import hu.kozma.backend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserEmail(String email);

    Optional<Reservation> findByIdAndUserEmail(Long id, String userEmail);
}
