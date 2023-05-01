package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.AccommodationDTO;
import hu.kozma.backend.dto.ReservationDTO;
import hu.kozma.backend.dto.SimpleIdDTO;
import hu.kozma.backend.mappers.MapperUtils;
import hu.kozma.backend.mappers.ReservationMapper;
import hu.kozma.backend.model.Reservation;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.AccommodationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
@RequestMapping("/accommodation")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("/all")
    public ResponseEntity<?> getAccommodations(@RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "address", required = false) String address,
                                               @RequestParam(value = "guests", required = false) Integer guests,
                                               @RequestParam(value = "from", required = false) Long from,
                                               @RequestParam(value = "end", required = false) Long end) {
        List<AccommodationDTO> accommodationDTOs = accommodationService.getAccommodations(name, address, guests, MapperUtils.toDate(from), MapperUtils.toDate(end));
        return RestResponseHandler.generateResponse(accommodationDTOs);
    }

    @GetMapping("/get_owned")
    public ResponseEntity<?> getAccommodations(Principal user) {
        List<AccommodationDTO> accommodationDTOs = accommodationService.getAccommodations(user.getName());
        return RestResponseHandler.generateResponse(accommodationDTOs);
    }

    @GetMapping("/get_details")
    public ResponseEntity<?> getAccommodationDetails(@RequestParam(value = "id") Long id) {
        AccommodationDTO accommodationDTO = accommodationService.getAccommodation(id);
        return RestResponseHandler.generateResponse(accommodationDTO);
    }

    @PostMapping(path = "/new", produces = {
            MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> addAccommodation(@RequestPart("files") List<MultipartFile> multipartFiles, @RequestPart("accommodation") AccommodationDTO accommodationDTO, Principal principal) throws Exception {
        accommodationService.saveAccommodation(accommodationDTO, multipartFiles, accommodationDTO.getMainImageIndex(), principal.getName());
        return RestResponseHandler.generateResponse("Szállás létrehozása sikeres!");
    }

    @PostMapping(path = "/modify",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> updateAccommodation(@RequestPart("files") List<MultipartFile> multipartFiles, @RequestPart("accommodation") AccommodationDTO accommodationDTO, Principal principal) throws Exception {
        accommodationService.modifyAccommodation(accommodationDTO, multipartFiles, accommodationDTO.getMainImageIndex(), principal.getName());
        return RestResponseHandler.generateResponse("Szállás módosítása sikeres!");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAccommodation(@RequestBody SimpleIdDTO deleteDTO, Principal principal) {
        accommodationService.deleteAccommodation(deleteDTO.getId(), principal.getName());
        return RestResponseHandler.generateResponse("A törlés sikeres!");
    }

    @PostMapping("/reserve")
    public ResponseEntity<?> addNewReservation(@RequestBody ReservationDTO reservationDTO, Principal principal) {
        Reservation reservation = ReservationMapper.toReservation(reservationDTO);
        accommodationService.reserveAccommodation(reservationDTO.getId(), reservation, principal.getName());
        return RestResponseHandler.generateResponse("A foglalás sikeres!");
    }
}
