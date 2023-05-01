package hu.kozma.backend.services;

import hu.kozma.backend.dto.ReviewDTO;
import hu.kozma.backend.mappers.ReviewMapper;
import hu.kozma.backend.model.Review;
import hu.kozma.backend.repository.AccommodationRepository;
import hu.kozma.backend.repository.ReservationRepository;
import hu.kozma.backend.repository.ReviewRepository;
import hu.kozma.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final AccommodationRepository accommodationRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public void addReview(ReviewDTO reviewDTO, String email) {
        Review review = ReviewMapper.toReview(reviewDTO);
        review.setUser(userRepository.findUserByEmail(email).orElseThrow(IllegalArgumentException::new));
        review.setAccommodation(accommodationRepository.findById(reviewDTO.getAccommodationId()).orElseThrow(IllegalArgumentException::new));
        review.setReservation(reservationRepository.findById(reviewDTO.getReservationId()).orElseThrow(IllegalArgumentException::new));
        reviewRepository.save(review);
    }

    public List<ReviewDTO> getReviews(String email) {
        return reviewRepository.findByUserEmail(email)
                .stream().map(ReviewMapper::toReviewDTO).toList();
    }
}
