package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.AnnounceDateDTO;
import hu.kozma.backend.model.AnnounceDate;

public class AnnounceDateMapper {
	public static AnnounceDate toAnnounceDate(AnnounceDateDTO announceDateDto) {
		AnnounceDate announceDate = new AnnounceDate();
		announceDate.setStartDate(MapperUtils.toDate(announceDateDto.getStartDate()));
		announceDate.setEndDate(MapperUtils.toDate(announceDateDto.getEndDate()));
		announceDate.setPrice(announceDateDto.getPrice());
		return announceDate;
	}

	public static AnnounceDateDTO toAnnounceDateDTO(AnnounceDate announceDate) {
		AnnounceDateDTO announceDateDto = new AnnounceDateDTO();
		announceDateDto.setStartDate(MapperUtils.toLongDate(announceDate.getStartDate()));
		announceDateDto.setEndDate(MapperUtils.toLongDate(announceDate.getEndDate()));
		announceDateDto.setPrice(announceDate.getPrice());
		return announceDateDto;
	}
}
