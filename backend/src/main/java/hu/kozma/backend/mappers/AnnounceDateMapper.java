package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.AnnounceDateDTO;
import hu.kozma.backend.model.AnnounceDate;

public class AnnounceDateMapper {
    public static AnnounceDate toAnnounceDate(AnnounceDateDTO announceDateDto) {
        AnnounceDate announceDate = new AnnounceDate();
        announceDate.setStartDate(MapperUtils.toDate(announceDateDto.getFrom()));
        announceDate.setEndDate(MapperUtils.toDate(announceDateDto.getEnd()));
        announceDate.setPrice(announceDateDto.getPrice());
        return announceDate;
    }

    public static AnnounceDateDTO toAnnounceDateDTO(AnnounceDate announceDate) {
        AnnounceDateDTO announceDateDto = new AnnounceDateDTO();
        announceDateDto.setFrom(MapperUtils.toLongDate(announceDate.getStartDate()));
        announceDateDto.setEnd(MapperUtils.toLongDate(announceDate.getEndDate()));
        announceDateDto.setPrice(announceDate.getPrice());
        return announceDateDto;
    }
}
