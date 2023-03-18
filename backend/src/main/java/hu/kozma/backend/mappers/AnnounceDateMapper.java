package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.AnnounceDateDto;
import hu.kozma.backend.model.AnnounceDate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class AnnounceDateMapper {
    public static AnnounceDate toAnnounceDate(AnnounceDateDto announceDateDto) {
        AnnounceDate announceDate = new AnnounceDate();
        announceDate.setStartDate(toDate(announceDateDto.getFromDate()));
        announceDate.setEndDate(toDate(announceDateDto.getEndDate()));
        announceDate.setPrice(announceDateDto.getPrice());
        return announceDate;
    }

    public static LocalDate toDate(Long date) {
        return date != null ? Instant.ofEpochMilli(date)
                .atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }
}
