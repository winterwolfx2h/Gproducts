package com.GestionProduit.Service;


import com.GestionProduit.Model.Review;

import java.util.List;

public interface ReviewService {

    Review submitReview(Review review);

    List<Review> getAllReviews();

    Review getReviewById(Long id);

    Review updateReview(Long id, Review review);

    void deleteReview(Long id);
}