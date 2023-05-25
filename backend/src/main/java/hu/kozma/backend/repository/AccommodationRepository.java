package hu.kozma.backend.repository;

import hu.kozma.backend.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
	@Query(value = "SELECT a from Accommodation a WHERE " +
			"(:name is null or a.name iLIKE  %:name%) and" +
			"(:address is null or a.address iLIKE  %:address%) and" +
			"(:guests is null or a.maxGuests >= :guests)")
	List<Accommodation> findFiltered(@Param("name") String name, @Param("address") String address, @Param("guests") Integer maxGuests);

	List<Accommodation> findByUserEmail(String userEmail);
}
