package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.AccommodationDTO;
import hu.kozma.backend.dto.ReservationDTO;
import hu.kozma.backend.dto.SimpleIdDTO;
import hu.kozma.backend.mappers.*;
import hu.kozma.backend.model.Accommodation;
import hu.kozma.backend.model.Image;
import hu.kozma.backend.model.Reservation;
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
                                 @RequestParam(value = "address", required = false) String address,
                                 @RequestParam(value = "guests", required = false) Integer guests,
                                 @RequestParam(value = "from", required = false) Long from,
                                 @RequestParam(value = "end", required = false) Long end
    ) {
        List<Accommodation> accommodations = accommodationService.getAccommodations(name, address, guests, MapperUtils.toDate(from), MapperUtils.toDate(end));
        List<AccommodationDTO> accommodationDTOs = accommodations.stream().map(accommodation -> {
            AccommodationDTO accommodationDTO = AccommodationMapper.toAccommodationDTO(accommodation);
            accommodationDTO.setReviews(accommodation.getReviews().stream().map(ReviewMapper::toReviewDTO).toList());
            try {
                Image mainImage = accommodation.getMainImage();
                accommodationDTO.setMainImage(fileSystemRepository.load(mainImage));
                accommodationDTO.setMainImageIndex(mainImage.getIndex());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return accommodationDTO;
        }).collect(Collectors.toList());

        return RestResponseHandler.generateResponse(accommodationDTOs);
    }

    @GetMapping("/get_owned")
    public ResponseEntity<?> owned(Principal user) {
        List<Accommodation> accommodations = accommodationService.getAccommodations(user.getName());
        List<AccommodationDTO> accommodationDTOs = accommodations.stream().map(accommodation -> {
            AccommodationDTO accommodationDTO = AccommodationMapper.toAccommodationDTO(accommodation);
            try {
                Image mainImage = accommodation.getMainImage();
                accommodationDTO.setMainImage(fileSystemRepository.load(mainImage));
                accommodationDTO.setMainImageIndex(mainImage.getIndex());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return accommodationDTO;
        }).collect(Collectors.toList());
        return RestResponseHandler.generateResponse(accommodationDTOs);
    }

    @GetMapping("/get_details")
    public ResponseEntity<?> get(@RequestParam(value = "id") Long id) {
        Accommodation accommodation = accommodationService.getAccommodation(id);
        if (accommodation == null)
            return RestResponseHandler.generateResponse("Nincs ilyen szállás!");
        AccommodationDTO accommodationDTO = AccommodationMapper.toAccommodationDTO(accommodation);
        accommodationDTO.setReservedDays(accommodationService.getReservedDays(accommodation));
        accommodationDTO.setImages(accommodation.getImages().stream().map(image -> {
            try {
                return fileSystemRepository.load(image);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList()));
        accommodationDTO.setAnnounces(accommodation.getAnnounces().stream()
                .map(AnnounceDateMapper::toAnnounceDateDTO).collect(Collectors.toList()));
        accommodationDTO.setReviews(accommodation.getReviews().stream().map(ReviewMapper::toReviewDTO).toList());
        return RestResponseHandler.generateResponse(accommodationDTO);
    }

    @PostMapping(path = "/new", produces = {
            MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> addNewAccommodation(@RequestPart("files") List<MultipartFile> multipartFiles, @RequestPart("accommodation") AccommodationDTO accommodationDTO, Principal principal) throws Exception {
        Accommodation accomodation = AccommodationMapper.toAccommodation(accommodationDTO);
        accommodationService.saveAccommodation(accomodation, multipartFiles, accommodationDTO.getMainImageIndex(), principal.getName());
        return RestResponseHandler.generateResponse("Szállás létrehozása sikeres!");
    }

    @PostMapping(path = "/modify", produces = {
            MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> modifyAccommodation(@RequestPart("files") List<MultipartFile> multipartFiles, @RequestPart("accommodation") AccommodationDTO accommodationDTO, Principal principal) throws Exception {
        Accommodation accomodation = AccommodationMapper.toAccommodation(accommodationDTO);

        accommodationService.modifyAccommodation(accomodation, multipartFiles, accommodationDTO.getMainImageIndex(), principal.getName());

        return RestResponseHandler.generateResponse("Szállás módosítása sikeres!");
    }

    @PostMapping("/reserve")
    public ResponseEntity<?> addNewReservation(@RequestBody ReservationDTO reservationDTO, Principal principal) {
        Reservation reservation = ReservationMapper.toReservation(reservationDTO);
        accommodationService.reserveAccommodation(reservationDTO.getId(), reservation, principal.getName());
        return RestResponseHandler.generateResponse("A foglalás sikeres!");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAccommodation(@RequestBody SimpleIdDTO deleteDTO, Principal principal) {
        accommodationService.deleteAccommodation(deleteDTO.getId(), principal.getName());
        return RestResponseHandler.generateResponse("A törlés sikeres!");
    }

}
