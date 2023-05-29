package hu.kozma.backend.service;

import hu.kozma.backend.exceptions.UsernameAlreadyTakenException;
import hu.kozma.backend.model.Accommodation;
import hu.kozma.backend.model.AnnounceDate;
import hu.kozma.backend.repository.AccommodationRepository;
import hu.kozma.backend.repository.FileSystemRepository;
import hu.kozma.backend.repository.UserRepository;
import hu.kozma.backend.services.AccommodationService;
import hu.kozma.backend.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static hu.kozma.backend.model.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccommodationServiceTest {

	@Autowired
	public AccommodationService accommodationService;
	@Autowired
	private AccommodationRepository accommodationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthenticationService authenticationService;
	@MockBean
	private FileSystemRepository fileSystemRepository;

	@Test
	public void announceDatesMergeToSingleTest() {
		Accommodation test = Accommodation.builder().announces(
				List.of(AnnounceDate.builder()
								.startDate(LocalDate.of(2012, 1, 1))
								.endDate(LocalDate.of(2012, 1, 31))
								.price(100.0).build(),
						AnnounceDate.builder()
								.startDate(LocalDate.of(2012, 2, 1))
								.endDate(LocalDate.of(2012, 2, 29))
								.price(100.0).build()
				)
		).build();

		List<AnnounceDate> announceDates = accommodationService.getMergedAnnounceDates(test.getAnnounces());
		List<AnnounceDate> expected = List.of(AnnounceDate.builder()
				.startDate(LocalDate.of(2012, 1, 1))
				.endDate(LocalDate.of(2012, 2, 29))
				.price(null).build());
		assertEquals(1, announceDates.size());
		assertEquals(expected, announceDates);
	}

	@Test
	public void announceDatesMergeToDoubleTest() {
		Accommodation test = Accommodation.builder().announces(
				List.of(AnnounceDate.builder()
								.startDate(LocalDate.of(2012, 1, 1))
								.endDate(LocalDate.of(2012, 1, 31))
								.price(100.0).build(),
						AnnounceDate.builder()
								.startDate(LocalDate.of(2012, 2, 2))
								.endDate(LocalDate.of(2012, 2, 29))
								.price(100.0).build()
				)
		).build();

		List<AnnounceDate> announceDates = accommodationService.getMergedAnnounceDates(test.getAnnounces());
		List<AnnounceDate> expected = List.of(AnnounceDate.builder()
						.startDate(LocalDate.of(2012, 1, 1))
						.endDate(LocalDate.of(2012, 1, 31))
						.price(null).build(),
				AnnounceDate.builder()
						.startDate(LocalDate.of(2012, 2, 2))
						.endDate(LocalDate.of(2012, 2, 29))
						.price(null).build());
		assertEquals(2, announceDates.size());
		assertEquals(expected, announceDates);
	}

	@Test
	public void getAccommodationFromDatabase() throws IOException, UsernameAlreadyTakenException {
		authenticationService.register(registerDTO());
		doReturn("path").when(fileSystemRepository).save(any(), any(), anyInt());
		MockMultipartFile file = new MockMultipartFile("files", "", MediaType.TEXT_PLAIN_VALUE, file());
		accommodationService.saveAccommodation(minimumSaveAccommodationDTO(), List.of(file), email());
		assertEquals(1, accommodationRepository.findAll().size());
	}
}
