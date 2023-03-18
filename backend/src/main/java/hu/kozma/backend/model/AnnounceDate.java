package hu.kozma.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "announce_date")
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnounceDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ToString.Exclude
    @JsonBackReference
    @ManyToOne
    private Accommodation accommodation;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "price", nullable = false)
    private Double price;
}
