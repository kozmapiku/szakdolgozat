package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.AccommodationDTO;
import hu.kozma.backend.dto.ImageDTO;
import hu.kozma.backend.mappers.AccommodationMapper;
import hu.kozma.backend.mappers.CommonMappers;
import hu.kozma.backend.model.Accommodation;
import hu.kozma.backend.repository.AccommodationRepository;
import hu.kozma.backend.repository.FileSystemRepository;
import hu.kozma.backend.repository.ImageRepository;
import hu.kozma.backend.repository.UserRepository;
import hu.kozma.backend.rest.RestResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/accommodation")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AccommodationController {

    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;
    private final FileSystemRepository fileSystemRepository;
    private final ImageRepository imageRepository;

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        List<Accommodation> accommodations = accommodationRepository.findAll();
        List<AccommodationDTO> accommodationDTOs = accommodations.stream().map((accommodation) -> {
                            AccommodationDTO accommodationDTO = AccommodationMapper.toAccommodationDTOList(accommodation);
                            ImageDTO imageDTO = new ImageDTO();
                            try {
                                imageDTO.setImage(CommonMappers.imageToBase64(fileSystemRepository.load(accommodation.getMainImage().get().getLocation())));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            accommodationDTO.setMainImage(imageDTO);
                            return accommodationDTO;
                        }
                )
                .collect(Collectors.toList());
        return RestResponseHandler.generateResponse(accommodationDTOs);
    }

    @PostMapping(path = "/new", produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = {  MediaType.MULTIPART_FORM_DATA_VALUE, APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> addNewAccommodation(@RequestPart("files") List<MultipartFile> multipartFiles, @RequestPart("accommodation") AccommodationDTO accommodationDTO) throws Exception {
        System.out.println(accommodationDTO);
        /*
        Optional<User> user = userRepository.findUserByEmail(principal.getName());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("A felhasználó nem található!");
        }

        Accommodation accommodation = AccommodationMapper.toAccommodation(accommodationDTO);
        accommodation.setUser(user.get());

        for (int i = 0; i < accommodationDTO.getListOfImages().size(); i++) {
            byte[] image = CommonMappers.base64ToImage(accommodationDTO.getListOfImages().get(i).getImage());
            String loc = fileSystemRepository.save(image, accommodation.getName() + "-" + i + "-" + accommodationDTO.getListOfImages().get(i).getName());
            Image imageModel = new Image();
            imageModel.setLocation(loc);
            accommodation.addImage(imageModel);
        }
        Accommodation newAccommodation = accommodationRepository.save(accommodation);
        System.out.println(newAccommodation);

         */
        return RestResponseHandler.generateResponse(null);
    }

}
