package hu.kozma.backend.services;

import hu.kozma.backend.model.Accommodation;
import hu.kozma.backend.model.Image;
import hu.kozma.backend.model.User;
import hu.kozma.backend.repository.AccommodationRepository;
import hu.kozma.backend.repository.FileSystemRepository;
import hu.kozma.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final FileSystemRepository fileSystemRepository;
    private final UserRepository userRepository;

    public void saveAccommodation(Accommodation accomodation, List<MultipartFile> multipartFiles, String name) throws IOException {
        for(int i = 0; i < multipartFiles.size(); i++)
            accomodation.addImage(new Image(fileSystemRepository.save(multipartFiles.get(i), name, i)));
        User user = userRepository.findUserByEmail(name).orElseThrow(() -> new EntityNotFoundException("A felhasználó nem található!"));
        accomodation.setUser(user);
        accommodationRepository.save(accomodation);
    }
}
