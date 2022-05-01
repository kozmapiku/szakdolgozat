package hu.kozma.backend.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "guest_number", nullable = false)
    private Integer guestNumber;

}
