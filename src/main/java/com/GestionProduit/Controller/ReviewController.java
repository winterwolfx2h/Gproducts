package com.GestionProduit.Controller;

import com.GestionProduit.Model.Review;
import com.GestionProduit.Service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Tag(name = "Review Controller", description = "Operations related to Reviews")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/reviews")
public class ReviewController {

  private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);


  final ReviewService reviewService;

  @PostMapping
  public ResponseEntity<Review> submitReview(@RequestBody Review review) {
    logger.info("Received request to submit review for product ID: {}", review.getProductId());
    Review savedReview = reviewService.submitReview(review);
    logger.info("Review submitted successfully with ID: {}", savedReview.getId());
    return ResponseEntity.ok(savedReview);
  }

  @GetMapping
  public ResponseEntity<List<Review>> getAllReviews() {
    logger.info("Received request to retrieve all reviews.");
    List<Review> reviews = reviewService.getAllReviews();
    logger.info("Retrieved {} reviews.", reviews.size());
    return ResponseEntity.ok(reviews);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
    logger.info("Received request to fetch review with ID: {}", id);
    Review review = reviewService.getReviewById(id);
    if (review != null) {
      logger.info("Review found: {}", review);
      return ResponseEntity.ok(review);
    } else {
      logger.warn("Review with ID: {} not found.", id);
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
    logger.info("Received request to update review with ID: {}", id);
    Review updatedReview = reviewService.updateReview(id, review);
    if (updatedReview != null) {
      logger.info("Review updated successfully: {}", updatedReview);
      return ResponseEntity.ok(updatedReview);
    } else {
      logger.warn("Update failed, review with ID: {} not found.", id);
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
    logger.info("Received request to delete review with ID: {}", id);
    reviewService.deleteReview(id);
    logger.info("Review with ID: {} deleted successfully.", id);
    return ResponseEntity.noContent().build();
  }
}