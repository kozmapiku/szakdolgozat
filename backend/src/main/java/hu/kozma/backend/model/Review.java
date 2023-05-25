package hu.kozma.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
	@OneToOne
	@JoinColumn(name = "reservation_id", referencedColumnName = "id")
	private Reservation reservation;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String comment;
	private Integer star;
}
