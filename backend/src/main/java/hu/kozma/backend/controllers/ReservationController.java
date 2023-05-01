package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.ReservationDTO;
import hu.kozma.backend.dto.SimpleIdDTO;
import hu.kozma.backend.mappers.ReservationMapper;
import hu.kozma.backend.model.Reservation;
import hu.kozma.backend.repository.FileSystemRepository;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;
    private final FileSystemRepository fileSystemRepository;

    @GetMapping("/get_owned")
    public ResponseEntity<?> owned(Principal principal) {
        List<Reservation> reservations = reservationService.getReservations(principal.getName());
        List<ReservationDTO> reservationDTOS = reservations.stream().map(ReservationMapper::toReservationDTO).map(reservation -> {
            try {
                reservation.getAccommodation().setMainImage(fileSystemRepository.load(reservation.getMainImageLocation()));
                return reservation;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
        return RestResponseHandler.generateResponse(reservationDTOS);
    }

    @GetMapping("/get_details")
    public ResponseEntity<?> getDetails(@RequestParam("id") Long id) {
        Reservation reservation = reservationService.getReservation(id);
        ReservationDTO reservationDTO = ReservationMapper.toReservationDTO(reservation);
        reservationDTO.getAccommodation().setImages(reservation.getAccommodation().getImages().stream().map(image -> {
            try {
                return fileSystemRepository.load(image);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList()));
        return RestResponseHandler.generateResponse(reservationDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAccommodation(@RequestBody SimpleIdDTO deleteDTO, Principal principal) {
        reservationService.deleteReservation(deleteDTO.getId(), principal.getName());
        return RestResponseHandler.generateResponse("A törlés sikeres!");
    }
}
