package hu.kozma.backend.services;

import hu.kozma.backend.dto.*;
import hu.kozma.backend.exceptions.AnnounceDateConflict;
import hu.kozma.backend.mappers.AccommodationMapper;
import hu.kozma.backend.mappers.ImageMapper;
import hu.kozma.backend.mappers.MapperUtils;
import hu.kozma.backend.mappers.ReviewMapper;
import hu.kozma.backend.model.*;
import hu.kozma.backend.repository.AccommodationRepository;
import hu.kozma.backend.repository.FileSystemRepository;
import hu.kozma.backend.repository.ReservationRepository;
import hu.kozma.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hu.kozma.backend.repository.FileSystemRepository.getImage;
import static hu.kozma.backend.repository.FileSystemRepository.getImages;

@Service
@AllArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final FileSystemRepository fileSystemRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public void saveAccommodation(SaveAccommodationDTO accommodationDTO, List<MultipartFile> multipartFiles, String name) throws IOException {
        User user = userRepository.findUserByEmail(name).orElseThrow(() -> new EntityNotFoundException("A felhasználó nem található!"));

        Accommodation accommodation = AccommodationMapper.toAccommodation(accommodationDTO);
        accommodation.addImage(new Image(fileSystemRepository.save(multipartFiles.get(0), name, 0), true));
        for (int i = 1; i < multipartFiles.size(); i++)
            accommodation.addImage(new Image(fileSystemRepository.save(multipartFiles.get(i), name, i), false));
        accommodation.setUser(user);
        accommodationRepository.save(accommodation);
    }

    public List<AccommodationDTO> getAccommodations(SearchAccommodationDTO searchAccommodationDTO) {
        List<Accommodation> accommodations = accommodationRepository.findFiltered(
                searchAccommodationDTO.getName(),
                searchAccommodationDTO.getAddress(),
                searchAccommodationDTO.getGuests());

        LocalDate startDateFilter = MapperUtils.toDate(searchAccommodationDTO.getFromDate());
        LocalDate endDateFilter = MapperUtils.toDate(searchAccommodationDTO.getEndDate());

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!Optional.ofNullable(searchAccommodationDTO.getShowOwn()).orElse(true) && email != null) {
            accommodations = accommodations.stream().filter(accommodation -> !accommodation.getUser().getEmail().equals(email)).toList();
        }

        if (startDateFilter != null && endDateFilter != null)
            accommodations = filterAccommodationsByDate(accommodations, startDateFilter, endDateFilter);
        else if (startDateFilter != null)
            accommodations = filterAccommodationsByStartDate(accommodations, startDateFilter);
        else if (endDateFilter != null) accommodations = filterAccommodationsByEndDate(accommodations, endDateFilter);

        return accommodations.stream().map(accommodation -> {
            AccommodationDTO accommodationDTO = AccommodationMapper.toAccommodationDTO(accommodation);
            accommodationDTO.setMainImage(getImage(accommodation.getMainImage()));
            return accommodationDTO;
        }).toList();
    }

    public List<AccommodationDTO> getAccommodations(String email) {
        return accommodationRepository.findByUserEmail(email).stream()
                .map(accommodation -> {
                    AccommodationDTO accommodationDTO = AccommodationMapper.toAccommodationDTO(accommodation);
                    accommodationDTO.setMainImage(getImage(accommodation.getMainImage()));
                    return accommodationDTO;
                }).toList();
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

    public AccommodationDetailsDTO getAccommodation(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        AccommodationDetailsDTO accommodationDetailsDTO = AccommodationMapper.toAccommodationDetailsDTO(accommodation);
        accommodationDetailsDTO.setImages(getImages(accommodation.getImages()));
        accommodationDetailsDTO.setReservedDays(getReservedDays(accommodation));
        return accommodationDetailsDTO;
    }

    public void reserveAccommodation(Long id, Reservation reservation, String userEmail) {
        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow();
        reservation.setAccommodation(accommodation);
        reservation.setUser(user);
        reservation.setPrice(calculatePrice(reservation, accommodation));
        reservationRepository.save(reservation);
    }

    private Double calculatePrice(Reservation reservation, Accommodation accommodation) {
        return reservation.getStartDate().datesUntil(reservation.getEndDate().plusDays(1))
                .mapToDouble(date -> getPriceForDay(date, accommodation))
                .reduce(0, Double::sum);
    }

    private Double getPriceForDay(LocalDate date, Accommodation accommodation) {
        return accommodation.getAnnounces().stream().filter(announceDate -> isWithinRange(date, announceDate))
                .findAny()
                .orElseThrow()
                .getPrice();
    }



    public void deleteAccommodation(Long id, String name) {
        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow();
        if (!accommodation.getUser().getEmail().equals(name)) {
            throw new IllegalArgumentException();
        }
        if (accommodation.getReservations().stream().anyMatch(reservation -> !reservation.isExpired())) {
            //TODO exception arra, hogy nem törli, amíg van aktív foglalás
            throw new IllegalArgumentException();
        }
        accommodationRepository.delete(accommodation);
    }

    public List<Long> getReservedDays(Accommodation accommodation) {
        List<Long> reservedDays = new ArrayList<>();
        accommodation.getReservations().forEach(reservation -> {
            LocalDate current = reservation.getStartDate();
            while (current.isBefore(reservation.getEndDate()) || current.isEqual(reservation.getEndDate())) {
                reservedDays.add(MapperUtils.toLongDate(current));
                current = current.plusDays(1);
            }
        });
        return reservedDays;
    }

    public void modifyAccommodation(UpdateAccommodationDTO accommodationDTO, List<MultipartFile> multipartFiles, Integer mainImageIndex, String name) throws IOException, AnnounceDateConflict {
        Accommodation accommodation = AccommodationMapper.toAccommodation(accommodationDTO);
        Accommodation old = accommodationRepository.findById(accommodation.getId()).orElseThrow();
        if (!old.getName().equals(accommodation.getName())) {
            old.setName(accommodation.getName());
        }
        if (!old.getAddress().equals(accommodation.getAddress())) {
            old.setAddress(accommodation.getAddress());
        }
        if (!old.getFloor().equals(accommodation.getFloor())) {
            old.setFloor(accommodation.getFloor());
        }
        if (!old.getDoor().equals(accommodation.getDoor())) {
            old.setDoor(accommodation.getDoor());
        }
        if (!old.getLat().equals(accommodation.getLat())) {
            old.setLat(accommodation.getLat());
        }
        if (!old.getLng().equals(accommodation.getLng())) {
            old.setLng(accommodation.getLng());
        }
        if (!old.getDescription().equals(accommodation.getDescription())) {
            old.setDescription(accommodation.getDescription());
        }
        if (!old.getMaxGuests().equals(accommodation.getMaxGuests())) {
            old.setMaxGuests(accommodation.getMaxGuests());
        }

        old.deleteImages();
        old.setImages(new HashSet<>());
        for (int i = 0; i < multipartFiles.size(); i++)
            old.addImage(new Image(fileSystemRepository.save(multipartFiles.get(i), name, i), i == mainImageIndex));


        List<AnnounceDate> newAnnounceDates = accommodation.getAnnounces();
        for (AnnounceDate announceDate : old.getAnnounces()) {
            if (!accommodation.getAnnounces().contains(announceDate)) {
                if (old.getReservations().stream()
                        .anyMatch(reservation ->
                                reservation.getStartDate().isAfter(announceDate.getStartDate().minusDays(1))
                                        && reservation.getEndDate().isBefore(announceDate.getEndDate().plusDays(1))
                                        && !reservation.isExpired())) {
                    throw new AnnounceDateConflict("Nem tudsz olyan meghírdetett időpontot megváltoztatni, amire van érvényes foglalás!");
                }
            }
        }
        old.deleteAnnounceDates();
        old.setAnnounces(newAnnounceDates);
        accommodationRepository.save(old);
    }

    public void deleteAnnounce(AnnounceDate announceDate) {

    }
}
