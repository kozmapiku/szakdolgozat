package hu.kozma.backend.repository;

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

    public String save(byte[] content, String imageName) throws Exception {
        Path newFile = Paths.get(RESOURCES_DIR + new Date().getTime() + "-" + imageName);
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, content);

        return newFile.toAbsolutePath()
                .toString();
    }

    public String save(MultipartFile multipartFile, String username, int index) throws IOException {
        Path newFile = Paths.get(RESOURCES_DIR+"/"+username+"/"+index+"-"+new Date().getTime()+"-"+multipartFile.getOriginalFilename());
        Files.createDirectories(newFile.getParent());
        multipartFile.transferTo(newFile);
        return newFile.toAbsolutePath().toString();
    }

    public byte[] load(String path) throws Exception {
        return Files.readAllBytes(Paths.get(path));
    }
}
