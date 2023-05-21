package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.*;
import hu.kozma.backend.mappers.MapperUtils;
import hu.kozma.backend.mappers.ReservationMapper;
import hu.kozma.backend.model.Accommodation;
import hu.kozma.backend.model.Reservation;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.AccommodationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
@RequestMapping("/api/accommodation")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("/all")
    public ResponseEntity<?> getAccommodations(SearchAccommodationDTO searchAccommodationDTO) {
        List<AccommodationDTO> accommodationDTOs = accommodationService.getAccommodations(searchAccommodationDTO);
        return RestResponseHandler.generateResponse(accommodationDTOs);
    }

    @GetMapping("/own")
    public ResponseEntity<?> getAccommodations(Principal user) {
        List<AccommodationDTO> accommodationDTOs = accommodationService.getAccommodations(user.getName());
        return RestResponseHandler.generateResponse(accommodationDTOs);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getAccommodationDetails(@RequestParam(value = "id") Long id) {
        AccommodationDetailsDTO accommodationDTO = accommodationService.getAccommodation(id);
        return RestResponseHandler.generateResponse(accommodationDTO);
    }

    @PostMapping(path = "/create",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> addAccommodation(@RequestPart("files") List<MultipartFile> multipartFiles,
                                              @Valid @RequestPart("accommodation") SaveAccommodationDTO accommodationDTO,
                                              Principal principal) throws Exception {
        accommodationService.saveAccommodation(accommodationDTO, multipartFiles, principal.getName());
        return RestResponseHandler.generateResponse("Szállás létrehozása sikeres!");
    }

    @PostMapping(path = "/update",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> updateAccommodation(@RequestPart("files") List<MultipartFile> multipartFiles,
                                                 @RequestPart("accommodation") UpdateAccommodationDTO accommodationDTO,
                                                 Principal principal) throws Exception {
        accommodationService.modifyAccommodation(accommodationDTO, multipartFiles, 0, principal.getName());
        return RestResponseHandler.generateResponse("Szállás módosítása sikeres!");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAccommodation(@RequestBody Long id, Principal principal) {
        accommodationService.deleteAccommodation(id, principal.getName());
        return RestResponseHandler.generateResponse("A törlés sikeres!");
    }
}
