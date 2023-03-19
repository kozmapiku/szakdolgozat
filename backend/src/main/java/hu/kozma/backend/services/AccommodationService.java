package hu.kozma.backend.services;

import hu.kozma.backend.model.*;
import hu.kozma.backend.repository.AccommodationRepository;
import hu.kozma.backend.repository.FileSystemRepository;
import hu.kozma.backend.repository.ReservationRepository;
import hu.kozma.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final FileSystemRepository fileSystemRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public void saveAccommodation(Accommodation accomodation, List<MultipartFile> multipartFiles, String name) throws IOException {
        for (int i = 0; i < multipartFiles.size(); i++)
            accomodation.addImage(new Image(fileSystemRepository.save(multipartFiles.get(i), name, i)));
        User user = userRepository.findUserByEmail(name).orElseThrow(() -> new EntityNotFoundException("A felhasználó nem található!"));
        accomodation.setUser(user);
        accommodationRepository.save(accomodation);
    }

    public List<Accommodation> getAccommodations(String name, Integer guests, LocalDate start, LocalDate end) {
        List<Accommodation> accommodations = accommodationRepository.findFiltered(name, guests);
        if (start != null && end != null) return filterAccommodationsByDate(accommodations, start, end);
        else if (start != null) return filterAccommodationsByStartDate(accommodations, start);
        else if (end != null) return filterAccommodationsByEndDate(accommodations, end);
        return accommodations;
    }

    private List<Accommodation> filterAccommodationsByDate(List<Accommodation> accommodations, LocalDate start, LocalDate end) {
        return accommodations.stream().filter(accommodation ->
                getMergedAnnounceDates(accommodation.getAnnounces()).stream().anyMatch(announceDate ->
                        isWithinRange(start, announceDate) && isWithinRange(end, announceDate))
        ).collect(Collectors.toList());
    }

    private List<Accommodation> filterAccommodationsByStartDate(List<Accommodation> accommodations, LocalDate start) {
        return accommodations.stream().filter(accommodation ->
                getMergedAnnounceDates(accommodation.getAnnounces()).stream().anyMatch(announceDate ->
                        isWithinRange(start, announceDate))
        ).collect(Collectors.toList());
    }

    private List<Accommodation> filterAccommodationsByEndDate(List<Accommodation> accommodations, LocalDate end) {
        return accommodations.stream().filter(accommodation ->
                getMergedAnnounceDates(accommodation.getAnnounces()).stream().anyMatch(announceDate ->
                        isWithinRange(end, announceDate))
        ).collect(Collectors.toList());
    }

    public List<AnnounceDate> getMergedAnnounceDates(List<AnnounceDate> announceDates) {
        if (announceDates.size() < 2)
            return announceDates;

        List<AnnounceDate> mergedAnnounceDates = new ArrayList<>();
        LocalDate startDate;
        LocalDate endDate;
        for (int i = 0; i < announceDates.size(); i++) {
            startDate = announceDates.get(i).getStartDate();
            endDate = announceDates.get(i).getEndDate();
            while (i + 1 < announceDates.size() && announceDates.get(i + 1).getStartDate().equals(endDate.plusDays(1))) {
                endDate = announceDates.get(++i).getEndDate();
            }
            mergedAnnounceDates.add(AnnounceDate.builder().startDate(startDate).endDate(endDate).build());
        }
        return mergedAnnounceDates;
    }

    public Boolean isWithinRange(LocalDate date, AnnounceDate announceDate) {
        return !(date.isBefore(announceDate.getStartDate()) || date.isAfter(announceDate.getEndDate()));
    }

    public Accommodation getAccommodation(Long id) {
        return accommodationRepository.findById(id).orElse(null);
    }

    public void reserveAccommodation(Long id, Reservation reservation, String userEmail) {
        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow();
        reservation.setAccommodation(accommodation);
        reservation.setUser(user);
        reservationRepository.save(reservation);
    }
}
