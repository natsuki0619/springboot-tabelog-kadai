package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewForm;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReviewService;

@Controller
@RequestMapping("/shops/{shopId}/review")
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final ShopRepository shopeRepository;
    private final ReviewService reviewService;

    public ReviewController(ReviewRepository reviewRepository, ShopRepository shopeRepository,
            ReviewService reviewService) {
        this.reviewRepository = reviewRepository;
        this.shopeRepository = shopeRepository;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String index(@PathVariable(name = "shopId") Integer shopId,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
            Model model) {
        Shop shop = shopeRepository.getReferenceById(shopId);
        Page<Review> reviewPage = reviewRepository.findByShopOrderByCreatedAtDesc(shop, pageable);

        model.addAttribute("shop", shop);
        model.addAttribute("reviewPage", reviewPage);

        return "review/index";
    }

    @GetMapping("/register")
    public String register(@PathVariable(name = "shopId") Integer shopId, Model model) {
        Shop shop = shopeRepository.getReferenceById(shopId);

        model.addAttribute("shop", shop);
        model.addAttribute("reviewForm", new ReviewForm());

        return "review/register";
    }

    @PostMapping("/create")
    public String create(@PathVariable(name = "shopId") Integer shopId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @ModelAttribute @Validated ReviewForm reviewForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {
        Shop shop = shopeRepository.getReferenceById(shopId);
        User user = userDetailsImpl.getUser();

        if (bindingResult.hasErrors()) {
            model.addAttribute("shop", shop);
            return "review/register";
        }

        reviewService.create(shop, user, reviewForm);
        redirectAttributes.addFlashAttribute("successMessage", "レビューを投稿しました。");

        return "redirect:/shops/{shopId}";
    }

    @GetMapping("/{reviewId}/edit")
    public String edit(@PathVariable(name = "shopId") Integer shopId, @PathVariable(name = "reviewId") Integer reviewId,
            Model model) {
        Shop shop = shopeRepository.getReferenceById(shopId);
        Review review = reviewRepository.getReferenceById(reviewId);

        ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(), review.getEvaluation(),
                review.getComments());

        model.addAttribute("shop", shop);
        model.addAttribute("review", review);
        model.addAttribute("reviewEditForm", reviewEditForm);

        return "review/edit";
    }

    @PostMapping("/{reviewId}/update")
    public String update(@PathVariable(name = "shopId") Integer shopId,
            @PathVariable(name = "reviewId") Integer reviewId,
            @ModelAttribute @Validated ReviewEditForm reviewEditForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        Shop shop = shopeRepository.getReferenceById(shopId);
        Review review = reviewRepository.getReferenceById(reviewId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("shop", shop);
            model.addAttribute("review", review);
            return "review/edit";
        }

        reviewService.update(reviewEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "レビューを編集しました。");

        return "redirect:/shops/{shopId}";
    }

    @PostMapping("/{reviewId}/delete")
    public String delete(@PathVariable(name = "reviewId") Integer reviewId, RedirectAttributes redirectAttributes) {
        reviewRepository.deleteById(reviewId);

        redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");

        return "redirect:/shops/{shopId}";
    }
}