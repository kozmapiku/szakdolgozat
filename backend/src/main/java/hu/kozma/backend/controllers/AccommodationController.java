package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.AccommodationDTO;
import hu.kozma.backend.mappers.AccommodationMapper;
import hu.kozma.backend.mappers.AnnounceDateMapper;
import hu.kozma.backend.model.Accommodation;
import hu.kozma.backend.repository.FileSystemRepository;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.AccommodationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
@RequestMapping("/accommodation")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AccommodationController {
    private final AccommodationService accommodationService;
    private final FileSystemRepository fileSystemRepository;

    @GetMapping("/all")
    public ResponseEntity<?> all(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "guests", required = false) Integer guests,
                                 @RequestParam(value = "from", required = false) Long from,
                                 @RequestParam(value = "end", required = false) Long end
    ) {
        List<Accommodation> accommodations = accommodationService.getAccommodations(name, guests, AnnounceDateMapper.toDate(from), AnnounceDateMapper.toDate(end));
        List<AccommodationDTO> accommodationDTOs = accommodations.stream().map(accommodation -> {
            AccommodationDTO accommodationDTO = AccommodationMapper.toAccommodationDTO(accommodation);
            try {
                accommodationDTO.setMainImage(fileSystemRepository.load(accommodation.getMainImage()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return accommodationDTO;
        }).collect(Collectors.toList());
        return RestResponseHandler.generateResponse(accommodationDTOs);
    }

    @PostMapping(path = "/new", produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = {  MediaType.MULTIPART_FORM_DATA_VALUE, APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> addNewAccommodation(@RequestPart("files") List<MultipartFile> multipartFiles, @RequestPart("accommodation") AccommodationDTO accommodationDTO, Principal principal) throws Exception {
        Accommodation accomodation = AccommodationMapper.toAccommodation(accommodationDTO);
        accommodationService.saveAccommodation(accomodation, multipartFiles, principal.getName());
        return RestResponseHandler.generateResponse(null);
    }

}
