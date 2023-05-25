package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.ImageDTO;
import hu.kozma.backend.model.Image;
import hu.kozma.backend.repository.FileSystemRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ImageMapper {

	private FileSystemRepository fileSystemRepository;

	public static ImageDTO toImageDTO(Image image) {
		ImageDTO imageDTO = new ImageDTO();
		return imageDTO;
	}
}
