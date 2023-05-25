package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.CompactReviewDTO;
import hu.kozma.backend.dto.ReviewDTO;
import hu.kozma.backend.dto.SaveReviewDTO;
import hu.kozma.backend.model.Review;

import static hu.kozma.backend.repository.FileSystemRepository.getImages;

public class ReviewMapper {
	public static CompactReviewDTO toCompactReviewDTO(Review review) {
		CompactReviewDTO reviewDTO = new CompactReviewDTO();
		reviewDTO.setStar(review.getStar());
		reviewDTO.setComment(review.getComment());
		reviewDTO.setName(review.getUser().getFullName());
		return reviewDTO;
	}

	public static Review toReview(SaveReviewDTO reviewDTO) {
		Review review = new Review();
		review.setStar(reviewDTO.getStar());
		review.setComment(reviewDTO.getComment());
		return review;
	}

	public static ReviewDTO toReviewDTO(Review review) {
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setComment(review.getComment());
		reviewDTO.setStar(review.getStar());
		reviewDTO.setReservationId(review.getReservation().getId());
		reviewDTO.setAccommodation(AccommodationMapper.toAccommodationAltDTO(review.getAccommodation()));
		reviewDTO.getAccommodation().setImages(getImages(review.getAccommodation().getImages()));
		return reviewDTO;
	}

}
