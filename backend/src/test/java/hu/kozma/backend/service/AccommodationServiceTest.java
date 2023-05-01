package hu.kozma.backend.service;

import hu.kozma.backend.model.Accommodation;
import hu.kozma.backend.model.AnnounceDate;
import hu.kozma.backend.services.AccommodationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccommodationServiceTest {

    @Autowired
    public AccommodationService accommodationService;

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
}
