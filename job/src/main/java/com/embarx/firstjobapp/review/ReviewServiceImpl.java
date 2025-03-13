package com.embarx.firstjobapp.review;

import com.embarx.firstjobapp.company.Company;
import com.embarx.firstjobapp.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean createReview(Long companyId, Review review) {
        Company company = companyService.findCompanyById(companyId);
        if (company != null){
            review.setCompany(company);
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
    public Review updateReview(Long companyId, Long reviewId, Review review) {
        Company company = companyService.findCompanyById(companyId);
        if(company!=null){
            Review reviewEntity = reviewRepository.findByCompanyIdAndId(
                    company.getId(),reviewId);
           if(reviewEntity!=null){
               reviewEntity.setCompany(company);
               reviewEntity.setDescription(review.getDescription());
               reviewEntity.setRating(review.getRating());
               reviewEntity.setTitle(review.getTitle());
              return reviewRepository.save(reviewEntity);
           }
        }
        return  null;
    }
}
