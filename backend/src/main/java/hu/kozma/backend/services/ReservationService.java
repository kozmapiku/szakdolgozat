package hu.kozma.backend.services;

import hu.kozma.backend.dto.ReservationDTO;
import hu.kozma.backend.dto.SaveReservation;
import hu.kozma.backend.dto.UpdateReservationDTO;
import hu.kozma.backend.mappers.ReservationMapper;
import hu.kozma.backend.model.Accommodation;
import hu.kozma.backend.model.Reservation;
import hu.kozma.backend.model.User;
import hu.kozma.backend.repository.AccommodationRepository;
import hu.kozma.backend.repository.ReservationRepository;
import hu.kozma.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static hu.kozma.backend.repository.FileSystemRepository.getImages;

@Service
@AllArgsConstructor
public class ReservationService {

	public final ReservationRepository reservationRepository;
	public final AccommodationRepository accommodationRepository;
	public final UserRepository userRepository;

	public List<ReservationDTO> getReservations(String email) {
		return reservationRepository.findByUserEmail(email).stream()
				.map(ReservationMapper::toReservationDTO)
				.toList();
	}

	public ReservationDTO getReservation(Long id, String email) {
		Reservation reservation = reservationRepository.findByIdAndUserEmail(id, email)
				.orElseThrow(() -> new EntityNotFoundException("A foglalás nem található."));
		ReservationDTO reservationDTO = ReservationMapper.toReservationDTO(reservation);
		reservationDTO.getAccommodation().setImages(getImages(reservation.getAccommodation().getImages()));
		return reservationDTO;
	}

	public void deleteReservation(Long id, String email) {
		Reservation reservation = reservationRepository.findByIdAndUserEmail(id, email)
				.orElseThrow(() -> new EntityNotFoundException("A foglalás nem található."));
		reservationRepository.delete(reservation);
	}

	public void reserveAccommodation(SaveReservation reservationDTO, String email) {
		Accommodation accommodation = accommodationRepository.findById(reservationDTO.getAccommodationId())
				.orElseThrow(() -> new EntityNotFoundException("A szállás nem található."));
		User user = userRepository.findUserByEmail(email).orElseThrow();
		Reservation reservation = ReservationMapper.toReservation(reservationDTO);
		reservation.setAccommodation(accommodation);
		reservation.setUser(user);
		reservation.setPrice(calculatePrice(reservation, accommodation));
		reservationRepository.save(reservation);
	}

	private Double calculatePrice(Reservation reservation, Accommodation accommodation) {
		return reservation.getStartDate().datesUntil(reservation.getEndDate().plusDays(1))
				.mapToDouble(date -> getPriceForDay(date, accommodation))
				.reduce(0, Double::sum);
	}

	private Double getPriceForDay(LocalDate date, Accommodation accommodation) {
		return accommodation.getAnnounces().stream()
				.filter(announceDate -> AccommodationService.isWithinRange(date, announceDate))
				.findAny()
				.orElseThrow(() -> new EntityNotFoundException("A meghírdetett időpont nem található."))
				.getPrice();
	}

	public void updateReservation(UpdateReservationDTO reservationDTO, String email) {
		Reservation reservation = reservationRepository.findByIdAndUserEmail(reservationDTO.getId(), email)
				.orElseThrow(() -> new EntityNotFoundException("A foglalás nem található."));
		checkReservationUpdate(reservation, reservationDTO);
		reservation.setGuestNumber(reservationDTO.getGuests());
		reservationRepository.save(reservation);
	}

	private void checkReservationUpdate(Reservation reservation, UpdateReservationDTO reservationDTO) {
		if (Objects.equals(reservation.getGuestNumber(), reservationDTO.getGuests())) {
			throw new IllegalArgumentException("A vendégek száma nem változott.");
		}
		if (reservation.getAccommodation().getMaxGuests() < reservationDTO.getGuests()) {
			throw new IllegalArgumentException("A vendégek száma nagyobb mint a megengedett.");
		}
	}
}
