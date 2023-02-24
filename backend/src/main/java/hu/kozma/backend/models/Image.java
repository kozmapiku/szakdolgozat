package hu.kozma.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
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
}
