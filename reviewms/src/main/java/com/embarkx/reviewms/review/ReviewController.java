package com.embarkx.reviewms.review;

import com.embarkx.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam Long companyId,
                                               @RequestBody Review review) {
        boolean result = reviewService.createReview(companyId, review);
        if (result){
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review added", HttpStatus.CREATED);
        }


        return new ResponseEntity<>("Review not added", HttpStatus.NOT_FOUND);
    }


    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId,
                                               @RequestBody Review review) {

        Review updatedReview = reviewService.updateReview(reviewId, review);
        if (updatedReview != null)
            return new ResponseEntity<>(updatedReview, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId) {

        Review reviewById = reviewService.getReviewById(reviewId);
        if(reviewById!=null)
            return new ResponseEntity<>(reviewById,HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getAverageRating(@RequestParam Long companyId){
        List<Review> allReviews = reviewService.getAllReviews(companyId);
       return  allReviews.stream().mapToDouble(Review::getRating)
               .average().orElse(0.0);
    }

}
