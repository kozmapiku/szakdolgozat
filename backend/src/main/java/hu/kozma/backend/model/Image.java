package hu.kozma.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "location")
    private String location;
    @ToString.Exclude
    @JsonBackReference
    @ManyToOne
    private Accommodation accommodation;
    @Column(name = "is_main")
    private boolean isMain;

    public Image(String location, boolean isMain) {
        this.location = location;
        this.isMain = isMain;
    }

    public void deleteAccommodation() {
        accommodation = null;
    }
}
