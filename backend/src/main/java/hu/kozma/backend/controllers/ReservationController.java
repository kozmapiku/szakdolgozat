package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.ReservationDTO;
import hu.kozma.backend.dto.SaveReservationDTO;
import hu.kozma.backend.dto.UpdateReservationDTO;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.ReservationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ReservationController {

	private final ReservationService reservationService;

	@GetMapping("/own")
	public ResponseEntity<?> getReservations(Principal principal) {
		List<ReservationDTO> reservationDTOs = reservationService.getReservations(principal.getName());
		return RestResponseHandler.generateResponse(reservationDTOs);
	}

	@GetMapping("/detail")
	public ResponseEntity<?> getReservationDetails(@RequestParam("id") Long id, Principal principal) {
		ReservationDTO reservationDTO = reservationService.getReservation(id, principal.getName());
		return RestResponseHandler.generateResponse(reservationDTO);
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteReservation(@RequestBody Long id, Principal principal) {
		reservationService.deleteReservation(id, principal.getName());
		return RestResponseHandler.generateResponse("A törlés sikeres!");
	}

	@PostMapping("/reserve")
	public ResponseEntity<?> addNewReservation(@Valid @RequestBody SaveReservationDTO reservationDTO, Principal principal) {
		reservationService.reserveAccommodation(reservationDTO, principal.getName());
		return RestResponseHandler.generateResponse("A foglalás sikeres!");
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateReservation(@Valid @RequestBody UpdateReservationDTO reservationDTO, Principal principal) {
		reservationService.updateReservation(reservationDTO, principal.getName());
		return RestResponseHandler.generateResponse("A foglalás módosítása sikeres!");
	}
}
