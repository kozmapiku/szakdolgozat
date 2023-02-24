package hu.kozma.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JsonBackReference
    private Accommodation accommodation;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String comment;
    private int star;
}
