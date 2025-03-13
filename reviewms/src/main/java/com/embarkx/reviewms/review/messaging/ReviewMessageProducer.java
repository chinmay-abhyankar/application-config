package com.embarkx.reviewms.review.messaging;

import com.embarkx.reviewms.review.Review;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {
    private RabbitTemplate rabbitTemplate;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public  void sendMessage(Review review){
        ReviewMessage reviewMessage = new ReviewMessage();
        reviewMessage.setCompanyId(review.getCompanyId());
        reviewMessage.setDescription(review.getDescription());
        reviewMessage.setId(review.getId());
        reviewMessage.setTitle(review.getTitle());
        reviewMessage.setRating(review.getRating());
        rabbitTemplate.convertAndSend("companyRatingQueue",reviewMessage);
    }
}
