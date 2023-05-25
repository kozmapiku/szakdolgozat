package hu.kozma.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "accommodation")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@Column(name = "address", nullable = false)
	private String address;
	@Column(name = "floor")
	private Integer floor;
	@Column(name = "door")
	private Integer door;
	@Column(name = "lat")
	private Float lat;
	@Column(name = "lng")
	private Float lng;
	@Column(name = "description", length = 2500)
	private String description;
	@Column(name = "max_guests", nullable = false)
	private Integer maxGuests;
	@JsonManagedReference
	@OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
	private List<AnnounceDate> announces = new ArrayList<>();
	@OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
	private List<Reservation> reservations = new ArrayList<>();
	@JsonManagedReference
	@OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
	private Set<Image> images = new HashSet<>();
	@JsonManagedReference
	@OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
	private Set<Review> reviews = new HashSet<>();

	public void addImage(Image image) {
		image.setAccommodation(this);
		images.add(image);
	}

	public void addAnnounceDate(AnnounceDate announceDate) {
		announceDate.setAccommodation(this);
		announces.add(announceDate);
	}

	public void deleteAnnounceDates() {
		this.announces.forEach(AnnounceDate::deleteAccommodation);
		announces.clear();
	}

	public void deleteImages() {
		this.images.forEach(Image::deleteAccommodation);
		images.clear();
	}

	public void addReview(Review review) {
		review.setAccommodation(this);
		reviews.add(review);
	}

	public Image getMainImage() {
		return images.stream().filter(Image::isMain).findFirst().orElseThrow(EntityNotFoundException::new);
	}

	public void deleteReservation(Reservation reservation) {
		reservations.remove(reservation);
		reservation.removeAccommodation();
	}
}
