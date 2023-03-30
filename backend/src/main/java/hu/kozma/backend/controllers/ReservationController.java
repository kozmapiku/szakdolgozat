package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.ReservationDTO;
import hu.kozma.backend.mappers.ReservationMapper;
import hu.kozma.backend.model.Reservation;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/get_owned")
    public ResponseEntity<?> owned(Principal principal) {
        List<Reservation> reservations = reservationService.getReservations(principal.getName());
        List<ReservationDTO> reservationDTOS = reservations.stream().map(ReservationMapper::toReservationDTO).toList();
        return RestResponseHandler.generateResponse(reservationDTOS);
    }
}
