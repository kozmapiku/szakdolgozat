package hu.kozma.backend.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "accommodation")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
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
    @OneToMany(mappedBy = "accommodation")
    Set<AnnounceDate> announces;
    @OneToMany(mappedBy = "accommodation")
    Set<Image> images;
    @OneToMany(mappedBy = "accommodation")
    Set<Review> reviews;
}
