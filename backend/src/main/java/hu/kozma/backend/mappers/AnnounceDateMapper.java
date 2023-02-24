package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.AnnounceDateDto;
import hu.kozma.backend.models.AnnounceDate;

import java.time.Instant;
import java.time.ZoneId;

public class AnnounceDateMapper {
    public static AnnounceDate toAnnounceDate(AnnounceDateDto announceDateDto) {
        AnnounceDate announceDate = new AnnounceDate();
        announceDate.setStartDate(Instant.ofEpochMilli(announceDateDto.getFromDate())
                .atZone(ZoneId.systemDefault()).toLocalDate());
        announceDate.setEndDate(Instant.ofEpochMilli(announceDateDto.getEndDate())
                .atZone(ZoneId.systemDefault()).toLocalDate());
        announceDate.setPrice(announceDateDto.getPrice());
        return announceDate;
    }
}
