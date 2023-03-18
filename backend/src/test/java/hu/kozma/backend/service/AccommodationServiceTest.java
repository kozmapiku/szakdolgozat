package hu.kozma.backend.service;

import hu.kozma.backend.model.Accommodation;
import hu.kozma.backend.model.AnnounceDate;
import hu.kozma.backend.services.AccommodationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class AccommodationServiceTest {

    @Autowired
    public AccommodationService accommodationService;

    @Test
    public void test() {
        Accommodation test = Accommodation.builder().announces(
                List.of(AnnounceDate.builder()
                        .startDate(LocalDate.of(2012, 1, 1))
                        .endDate(LocalDate.of(2012, 1, 31)).build())
        ).build();

        List<AnnounceDate> announceDates = accommodationService.getMergedAnnounceDates(test.getAnnounces());
        System.out.println(announceDates);
    }
}
