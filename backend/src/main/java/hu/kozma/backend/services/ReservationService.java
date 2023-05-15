package hu.kozma.backend.services;

import hu.kozma.backend.dto.ReservationDTO;
import hu.kozma.backend.mappers.ReservationMapper;
import hu.kozma.backend.model.Reservation;
import hu.kozma.backend.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static hu.kozma.backend.repository.FileSystemRepository.getImage;
import static hu.kozma.backend.repository.FileSystemRepository.getImages;

@Service
@AllArgsConstructor
public class ReservationService {

    public final ReservationRepository reservationRepository;

    public List<ReservationDTO> getReservations(String email) {
        List<Reservation> reservations = reservationRepository.findByUserEmail(email);
        return reservations.stream()
                .map(ReservationMapper::toReservationDTO)
                .peek(reservationDTO -> reservationDTO.getAccommodation().setMainImage(getImage(reservationDTO.getMainImageLocation())))
                .toList();
    }

    public ReservationDTO getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        ReservationDTO reservationDTO = ReservationMapper.toReservationDTO(reservation);
        reservationDTO.getAccommodation().setImages(getImages(reservation.getAccommodation().getImages()));
        return reservationDTO;
    }

    public void deleteReservation(Long id, String email) {
        Reservation reservation = reservationRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(IllegalArgumentException::new);
        reservationRepository.delete(reservation);
    }
}
