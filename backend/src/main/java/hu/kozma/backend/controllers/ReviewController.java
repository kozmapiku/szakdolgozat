package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.ReviewDTO;
import hu.kozma.backend.dto.SaveReviewDTO;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ReviewController {

	private ReviewService reviewService;

	@PostMapping("/create")
	public ResponseEntity<?> review(@Valid @RequestBody SaveReviewDTO reviewDTO, Principal principal) {
		reviewService.addReview(reviewDTO, principal.getName());
		return RestResponseHandler.generateResponse("Az értékelés sikeres!");
	}

	@GetMapping("/own")
	public ResponseEntity<?> getReviews(Principal principal) {
		List<ReviewDTO> reviewList = reviewService.getReviews(principal.getName());
		return RestResponseHandler.generateResponse(reviewList);
	}
}
