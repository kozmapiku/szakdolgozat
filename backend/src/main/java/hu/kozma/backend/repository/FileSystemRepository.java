package hu.kozma.backend.repository;

import hu.kozma.backend.model.Image;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public class FileSystemRepository {
	private final String RESOURCES_DIR = "uploads";

	public static byte[] load(Image image) throws Exception {
		return Files.readAllBytes(Paths.get(image.getLocation()));
	}

	public static byte[] load(String location) throws Exception {
		return Files.readAllBytes(Paths.get(location));
	}

	public static List<byte[]> getImages(Set<Image> modelImages) {
		return modelImages.stream().map(image -> {
			try {
				return load(image);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).toList();
	}

	public static byte[] getImage(String location) {
		try {
			return load(location);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] getImage(Image location) {
		try {
			return load(location);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String save(MultipartFile multipartFile, String username, int index) throws IOException {
		Path newFile = Paths.get(RESOURCES_DIR + "/" + username + "/" + index + "-" + new Date().getTime() + "-" + multipartFile.getOriginalFilename());
		Files.createDirectories(newFile.getParent());
		multipartFile.transferTo(newFile);
		return newFile.toAbsolutePath().toString();
	}
}
