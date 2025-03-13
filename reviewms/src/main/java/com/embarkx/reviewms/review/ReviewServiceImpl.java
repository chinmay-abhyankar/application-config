package com.embarkx.reviewms.review;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean createReview(Long companyId, Review review) {

        if (companyId != null && review!=null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return  true;
        }
        return false;
    }

    @Override
    public Review findByCompanyIdAndReviewId(Long companyId, Long reviewId) {
       return  reviewRepository.findByCompanyIdAndId(companyId,reviewId);
    }

    @Override
    public Review updateReview(Long reviewId, Review review) {
        if(reviewId!=null){
            Review reviewEntity = reviewRepository.findById(reviewId).orElse(null);
           if(reviewEntity!=null){
               reviewEntity.setDescription(review.getDescription());
               reviewEntity.setRating(review.getRating());
               reviewEntity.setTitle(review.getTitle());
              return reviewRepository.save(reviewEntity);
           }
        }
        return  null;
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }
}
