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
    @Column(name = "max_guests", nullable = false)
    private Integer maxGuests;
    @Enumerated(EnumType.STRING)
    @Column(name = "city", nullable = false)
    private City city;
    @Column(name = "address", nullable = false)
    private String address;
    @JsonManagedReference
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private List<AnnounceDate> announces = new ArrayList<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "accommodation")
    private Set<Review> reviews = new HashSet<>();

    public void addImage(Image image) {
        image.setAccommodation(this);
        images.add(image);
    }

    public void addAnnounceDate(AnnounceDate announceDate) {
        announceDate.setAccommodation(this);
        announces.add(announceDate);
    }

    public void addReview(Review review) {
        review.setAccommodation(this);
        reviews.add(review);
    }

    public Image getMainImage() {
        return images.stream().filter(Image::isMain).findFirst().orElse(images.stream().findFirst().get());
    }
}
