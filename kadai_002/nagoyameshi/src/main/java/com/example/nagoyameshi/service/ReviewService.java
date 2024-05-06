package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewForm;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, ShopRepository shopRepository,
            UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void create(Shop shop, User user, ReviewForm reviewForm) {
        Review review = new Review();
        review.setShop(shop);
        review.setUser(user);
        review.setEvaluation(reviewForm.getEvaluation());
        review.setComments(reviewForm.getComments());
        reviewRepository.save(review);
    }

    @Transactional
    public void update(ReviewEditForm reviewEditForm) {
        Review review = reviewRepository.getReferenceById(reviewEditForm.getId());
        review.setEvaluation(reviewEditForm.getEvaluation());
        review.setComments(reviewEditForm.getComments());
        reviewRepository.save(review);
    }

    public boolean hasUserAlreadyReviewed(Shop shop, User user) {
        return reviewRepository.findByShopAndUser(shop, user) != null;
    }
}
