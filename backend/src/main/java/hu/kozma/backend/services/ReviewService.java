package hu.kozma.backend.services;

import hu.kozma.backend.dto.CompactReviewDTO;
import hu.kozma.backend.dto.ReviewDTO;
import hu.kozma.backend.dto.SaveReviewDTO;
import hu.kozma.backend.mappers.ReviewMapper;
import hu.kozma.backend.model.Review;
import hu.kozma.backend.repository.AccommodationRepository;
import hu.kozma.backend.repository.ReservationRepository;
import hu.kozma.backend.repository.ReviewRepository;
import hu.kozma.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public void addReview(SaveReviewDTO reviewDTO, String email) {
        Review review = ReviewMapper.toReview(reviewDTO);
        review.setUser(userRepository.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("A felhasználó nem található.")));
        review.setAccommodation(accommodationRepository.findById(reviewDTO.getAccommodationId()).orElseThrow(() -> new EntityNotFoundException("A szállás nem található")));
        review.setReservation(reservationRepository.findById(reviewDTO.getReservationId()).orElseThrow(() -> new EntityNotFoundException("A foglalás nem található.")));
        reviewRepository.save(review);
    }

    public List<ReviewDTO> getReviews(String email) {
        return reviewRepository.findByUserEmail(email)
                .stream().map(ReviewMapper::toReviewDTO).toList();
    }
}
