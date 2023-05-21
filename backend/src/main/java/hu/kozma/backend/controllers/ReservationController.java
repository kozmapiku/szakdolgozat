package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.ReservationDTO;
import hu.kozma.backend.dto.SimpleIdDTO;
import hu.kozma.backend.mappers.ReservationMapper;
import hu.kozma.backend.model.Reservation;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.ReservationService;
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
    public ResponseEntity<?> getReservationDetails(@RequestParam("id") Long id) {
        ReservationDTO reservationDTO = reservationService.getReservation(id);
        return RestResponseHandler.generateResponse(reservationDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteReservation(@RequestBody Long id, Principal principal) {
        reservationService.deleteReservation(id, principal.getName());
        return RestResponseHandler.generateResponse("A törlés sikeres!");
    }
/*
    @PostMapping("/reserve")
    public ResponseEntity<?> addNewReservation(@RequestBody ReservationDTO reservationDTO, Principal principal) {
        Reservation reservation = ReservationMapper.toReservation(reservationDTO);
        accommodationService.reserveAccommodation(reservationDTO.getId(), reservation, principal.getName());
        return RestResponseHandler.generateResponse("A foglalás sikeres!");
    }

 */
}
