package hu.kozma.backend.repository;

import hu.kozma.backend.model.AnnounceDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnounceDateRepository extends JpaRepository<AnnounceDate, Long> {
}