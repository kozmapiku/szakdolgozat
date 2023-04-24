package hu.kozma.backend.services;

import hu.kozma.backend.dto.ReviewDTO;
import hu.kozma.backend.model.Review;
import hu.kozma.backend.repository.AccommodationRepository;
import hu.kozma.backend.repository.ReservationRepository;
import hu.kozma.backend.repository.ReviewRepository;
import hu.kozma.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {
    private final AccommodationRepository accommodationRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public void review(ReviewDTO reviewDTO, String name) {
        Review review = new Review();
        review.setAccommodation(accommodationRepository.findById(reviewDTO.getAccommodationId()).orElseThrow(IllegalArgumentException::new));
        review.setReservation(reservationRepository.findById(reviewDTO.getReservationId()).orElseThrow(IllegalArgumentException::new));
        review.setUser(userRepository.findUserByEmail(name).orElseThrow(IllegalArgumentException::new));
        review.setStar(reviewDTO.getStar());
        if (reviewDTO.getComment() != null)
            review.setComment(reviewDTO.getComment());
        reviewRepository.save(review);
    }
}
