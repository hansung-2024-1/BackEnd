package ahchacha.ahchacha.controller;

import ahchacha.ahchacha.domain.Item;
import ahchacha.ahchacha.domain.ItemReview;
import ahchacha.ahchacha.domain.common.enums.Category;
import ahchacha.ahchacha.dto.ItemDto;
import ahchacha.ahchacha.dto.ReviewDto;
import ahchacha.ahchacha.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "리뷰 목록 조회")
    @GetMapping("/list")
    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getAllReviews(@RequestParam(value = "page", defaultValue = "1") int page) {
        Page<ReviewDto.ReviewResponseDto> reviewsResponseDto = reviewService.getAllReviews(page);
        return new ResponseEntity<>(reviewsResponseDto, HttpStatus.OK);
    }

    // 리뷰 등록
    @Operation(summary = "리뷰 등록하기")
    @PostMapping("/{userId}/{itemId}")
    public ResponseEntity<ItemReview> saveReview(@PathVariable Long userId, @PathVariable Long itemId, @RequestBody ReviewDto.ReviewRequestDto reviewDto) {
        try {
            ItemReview savedReview = reviewService.saveReview(userId, itemId, reviewDto);
            return ResponseEntity.ok(savedReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "아이템 아이디로 리뷰 조회")
    @GetMapping("/{itemId}/reviews")
    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getReviewsByItemId(@RequestParam(value = "page", defaultValue = "1") int page, @PathVariable Long itemId) {
        Page<ReviewDto.ReviewResponseDto> reviewPage = reviewService.getReviewsByItemId(itemId, page);
        return ResponseEntity.ok(reviewPage);
    }

    /*
    @GetMapping("/{itemId}/reviews/high-score")
    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getReviewsByItemIdAndHighScore(@RequestParam(value = "page", defaultValue = "1") int page, @PathVariable Long itemId) {
        Page<ReviewDto.ReviewResponseDto> reviewPage = reviewService.getReviewsByItemIdAndHighScore(page);
        return ResponseEntity.ok(reviewPage);
    }

    @GetMapping("/{itemId}/reviews/low-score")
    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getReviewsByItemIdAndLowScore(@RequestParam(value = "page", defaultValue = "1") int page, @PathVariable Long itemId) {
        Page<ReviewDto.ReviewResponseDto> reviewPage = reviewService.getReviewsByItemIdAndLowScore(page);
        return ResponseEntity.ok(reviewPage);
    }

    @GetMapping("/{userId}/reviews")
    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getReviewsByUserId(@RequestParam(value = "page", defaultValue = "1") int page, @PathVariable Long userId) {
        Page<ReviewDto.ReviewResponseDto> reviewPage = reviewService.getReviewsByUserId(page);
        return ResponseEntity.ok(reviewPage);
    }

    @GetMapping("/{userId}/reviews/high-score")
    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getReviewsByUserIdAndHighScore(@RequestParam(value = "page", defaultValue = "1") int page, @PathVariable Long userId) {
        Page<ReviewDto.ReviewResponseDto> reviewPage = reviewService.getReviewsByUserIdAndHighScore(page);
        return ResponseEntity.ok(reviewPage);
    }

    @GetMapping("/{userId}/reviews/low-score")
    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getReviewsByUserIdAndLowScore(@RequestParam(value = "page", defaultValue = "1") int page, @PathVariable Long userId) {
        Page<ReviewDto.ReviewResponseDto> reviewPage = reviewService.getReviewsByUserIdAndLowScore(page);
        return ResponseEntity.ok(reviewPage);
    }
     */
}

