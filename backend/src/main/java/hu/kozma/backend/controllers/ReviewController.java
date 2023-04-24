package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.ReviewDTO;
import hu.kozma.backend.rest.RestResponseHandler;
import hu.kozma.backend.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ReviewController {
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> review(@RequestBody ReviewDTO reviewDTO, Principal principal) {
        reviewService.review(reviewDTO, principal.getName());
        return RestResponseHandler.generateResponse(reviewDTO);
    }
}
