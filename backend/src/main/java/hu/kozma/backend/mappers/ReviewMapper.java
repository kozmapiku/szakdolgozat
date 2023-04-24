package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.ReviewDTO;
import hu.kozma.backend.model.Review;

public class ReviewMapper {
    public static ReviewDTO toReviewDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setStar(review.getStar());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setUserName(review.getUser().getEmail());
        return reviewDTO;
    }
}
