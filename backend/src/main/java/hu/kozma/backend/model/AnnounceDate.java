package hu.kozma.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnnounceDate c)) {
            return false;
        }

        return startDate.isEqual(c.startDate)
                && endDate.isEqual(c.endDate) &&
                Objects.equals(price, c.price);
    }

    public void deleteAccommodation() {
        accommodation = null;
    }
}
