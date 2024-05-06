package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;
import com.example.nagoyameshi.service.ReviewService;

@Controller
@RequestMapping("/shops")

public class ShopController {
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
    private final FavoriteRepository favoriteRepository;
    private final FavoriteService favoriteService;

    public ShopController(ShopRepository shopRepository, CategoryRepository categoryRepository,
            ReviewRepository reviewRepository, ReviewService reviewService, FavoriteRepository favoriteRepository,
            FavoriteService favoriteService) {
        this.shopRepository = shopRepository;
        this.categoryRepository = categoryRepository;
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
        this.favoriteRepository = favoriteRepository;
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "area", required = false) String area,
            @RequestParam(name = "CategoryId", required = false) Integer CategoryId,
            @RequestParam(name = "HighestPrice", required = false) Integer highestPrice,
            @RequestParam(name = "order", required = false) String order,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
            Model model) {
        Page<Shop> shopPage;
        List<Category> category = categoryRepository.findAll();

        if (keyword != null && !keyword.isEmpty()) {
            if (order != null && order.equals("highestPriceAsc")) {
                shopPage = shopRepository.findByNameLikeOrAddressLikeOrderByHighestPriceAsc("%" + keyword + "%",
                        "%" + keyword + "%", pageable);
            } else {
                shopPage = shopRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%",
                        "%" + keyword + "%", pageable);
            }
        } else if (area != null && !area.isEmpty()) {
            if (order != null && order.equals("highestPriceAsc")) {
                shopPage = shopRepository.findByAddressLikeOrderByHighestPriceAsc("%" + area + "%", pageable);

            } else {
                shopPage = shopRepository.findByAddressLikeOrderByCreatedAtDesc("%" + area + "%", pageable);
            }

        } else if (CategoryId != null) {
            if (order != null && order.equals("category")) {
                shopPage = shopRepository.findByCategoryId(CategoryId, pageable);
            } else if (order != null && order.equals("categoryId")) {
                shopPage = shopRepository.findByCategoryId(CategoryId, pageable);
            } else {
                shopPage = shopRepository.findByCategoryIdOrderByCreatedAtDesc(CategoryId, pageable);
            }

        } else if (highestPrice != null) {
            if (order != null && order.equals("highestPriceAsc")) {
                shopPage = shopRepository.findByHighestPriceLessThanEqualOrderByHighestPriceAsc(highestPrice, pageable);
            } else {
                shopPage = shopRepository.findByHighestPriceLessThanEqualOrderByCreatedAtDesc(highestPrice, pageable);
            }
        } else {
            if (order != null && order.equals("highestPriceAsc")) {
                shopPage = shopRepository.findAllByOrderByHighestPriceAsc(pageable);

            } else {
                shopPage = shopRepository.findAllByOrderByCreatedAtDesc(pageable);
            }

        }

        model.addAttribute("shopPage", shopPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("area", area);
        model.addAttribute("highestPrice", highestPrice);
        model.addAttribute("order", order);
        model.addAttribute("category", category);
        model.addAttribute("categoryId", CategoryId);

        return "shops/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        Shop shop = shopRepository.getReferenceById(id);

        boolean hasUserAlreadyReviewed = false;
        Favorite favorite = null;
        boolean isFavorite = false;
        if (userDetailsImpl != null) {
            User user = userDetailsImpl.getUser();
            hasUserAlreadyReviewed = reviewService.hasUserAlreadyReviewed(shop, user);
            isFavorite = favoriteService.isFavorite(shop, user);
            if (isFavorite) {
                favorite = favoriteRepository.findByShopAndUser(shop, user);
            }
        }

        List<Review> newReviews = reviewRepository.findTop8ByShopOrderByCreatedAtDesc(shop);
        long totalReviewCount = reviewRepository.countByShop(shop);

        model.addAttribute("shop", shop);
        model.addAttribute("reservationInputForm", new ReservationInputForm());
        model.addAttribute("hasUserAlreadyReviewed", hasUserAlreadyReviewed);
        model.addAttribute("totalReviewCount", totalReviewCount);
        model.addAttribute("newReviews", newReviews);
        model.addAttribute("favorite", favorite);
        model.addAttribute("isFavorite", isFavorite);

        return "shops/show";
    }
}
