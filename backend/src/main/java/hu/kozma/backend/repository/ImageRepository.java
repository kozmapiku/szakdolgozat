package hu.kozma.backend.repository;

import hu.kozma.backend.models.Accommodation;
import hu.kozma.backend.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
