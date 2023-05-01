package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.ReviewDTO;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ReviewController {

    private ReviewService reviewService;

    @PostMapping("send")
    public ResponseEntity<?> review(@RequestBody ReviewDTO reviewDTO, Principal principal) {
        reviewService.addReview(reviewDTO, principal.getName());
        return RestResponseHandler.generateResponse("Az értékelés sikeres!");
    }

    @GetMapping("/mine")
    public ResponseEntity<?> mine(Principal principal) {
        List<ReviewDTO> reviewList = reviewService.getReviews(principal.getName());
        return RestResponseHandler.generateResponse(reviewList);
    }
}
