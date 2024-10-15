package com.GestionProduit.Service.serviceImpl;


import com.GestionProduit.Model.Review;
import com.GestionProduit.NLP.NLPService;
import com.GestionProduit.Repository.ReviewRepository;
import com.GestionProduit.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    final ReviewRepository reviewRepository;
    final NLPService nlpService;

    @Override
    public Review submitReview(Review review) {
        logger.info("Submitting review for product ID: {}", review.getProductId());
        String sentiment = nlpService.analyzeSentiment(review.getContent());
        review.setSentiment(sentiment);

        Review savedReview = reviewRepository.save(review);
        logger.info("Review submitted successfully with sentiment: {}", sentiment);

        return savedReview;
    }

    @Override
    public List<Review> getAllReviews() {
        logger.info("Retrieving all reviews.");
        List<Review> reviews = reviewRepository.findAll();
        logger.info("Retrieved {} reviews.", reviews.size());
        return reviews;
    }

    @Override
    public Review getReviewById(Long id) {
        logger.info("Fetching review with ID: {}", id);
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isPresent()) {
            logger.info("Review found: {}", optionalReview.get());
        } else {
            logger.warn("Review with ID: {} not found.", id);
        }
        return optionalReview.orElse(null);
    }

    @Override
    public Review updateReview(Long id, Review review) {
        logger.info("Updating review with ID: {}", id);
        if (reviewRepository.existsById(id)) {
            review.setId(id);
            Review updatedReview = reviewRepository.save(review);
            logger.info("Review updated successfully: {}", updatedReview);
            return updatedReview;
        }
        logger.warn("Update failed, review with ID: {} not found.", id);
        return null;
    }

    @Override
    public void deleteReview(Long id) {
        logger.info("Deleting review with ID: {}", id);
        reviewRepository.deleteById(id);
        logger.info("Review with ID: {} deleted successfully.", id);
    }
}