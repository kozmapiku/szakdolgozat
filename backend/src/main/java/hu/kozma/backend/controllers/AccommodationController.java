package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.AccommodationDTO;
import hu.kozma.backend.exceptions.UserNotLoggedInException;
import hu.kozma.backend.mappers.AccommodationMapper;
import hu.kozma.backend.models.Accommodation;
import hu.kozma.backend.models.User;
import hu.kozma.backend.repository.AccommodationRepository;
import hu.kozma.backend.repository.UserRepository;
import hu.kozma.backend.rest.RestResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/accommodation")
public class AccommodationController {

    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Accommodation>> all() {
        return new ResponseEntity<>(accommodationRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> addNewAccommodation(@RequestBody AccommodationDTO accommodationDTO, Principal principal) throws EntityNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(principal.getName());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found!");
        }
        Accommodation accommodation = AccommodationMapper.toAccommodation(accommodationDTO);
        accommodation.setUser(user.get());
        accommodationRepository.save(accommodation);
        return RestResponseHandler.generateResponse(accommodation);
    }

}
