package hu.kozma.backend.repository;

import hu.kozma.backend.model.Image;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
public class FileSystemRepository {
    //TODO implement a good resource dir mechanism
    private final String RESOURCES_DIR = "C:/res/";

    public String save(MultipartFile multipartFile, String username, int index) throws IOException {
        Path newFile = Paths.get(RESOURCES_DIR + "/" + username + "/" + index + "-" + new Date().getTime() + "-" + multipartFile.getOriginalFilename());
        Files.createDirectories(newFile.getParent());
        multipartFile.transferTo(newFile);
        return newFile.toAbsolutePath().toString();
    }

    public byte[] load(Image image) throws Exception {
        return Files.readAllBytes(Paths.get(image.getLocation()));
    }

    public byte[] load(String location) throws Exception {
        return Files.readAllBytes(Paths.get(location));
    }
}
