package com.embarx.firstjobapp.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    boolean createReview(Long companyId,Review review);
    Review findByCompanyIdAndReviewId(Long companyId,Long reviewId);
    Review updateReview(Long companyId,Long reviewId,Review review);
}
