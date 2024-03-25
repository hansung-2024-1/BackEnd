package ahchacha.ahchacha.controller;

import ahchacha.ahchacha.domain.Review;
import ahchacha.ahchacha.dto.ReviewDto;
import ahchacha.ahchacha.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Operation(summary = "리뷰 등록하기")
    @PostMapping()
    public ResponseEntity<ReviewDto.ReviewResponseDto> createReview(@RequestBody ReviewDto.ReviewRequestDto reviewRequestDto, HttpSession session) {

        ReviewDto.ReviewResponseDto reviewResponseDto = reviewService.createReview(reviewRequestDto, session);
        return new ResponseEntity<>(reviewResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "리뷰 목록 조회 RENTER")
    @GetMapping("/list-renter")
    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getAllReviewsRENTER(@RequestParam(value = "page", defaultValue = "1") int page) {
        Page<ReviewDto.ReviewResponseDto> reviewsResponseDto = reviewService.getAllReviewsRENTER(page);
        return new ResponseEntity<>(reviewsResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "리뷰 목록 조회 RECEIVER")
    @GetMapping("/list-receiver")
    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getAllReviewsRECEIVER(@RequestParam(value = "page", defaultValue = "1") int page) {
        Page<ReviewDto.ReviewResponseDto> reviewsResponseDto = reviewService.getAllReviewsRECEIVER(page);
        return new ResponseEntity<>(reviewsResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "아이템 아이디로 리뷰 조회", description = "{itemId} 자리에 상세 조회할 아이템 id를 전달해주세요.")
    @GetMapping("/{itemId}")
    public ResponseEntity<ReviewDto.ReviewResponseDto> getReviewByItemId(@PathVariable Long itemId) {
        ReviewDto.ReviewResponseDto review = reviewService.getReviewByItemId(itemId);
        return ResponseEntity.ok(review);
    }

//    @Operation(summary="아이템 아이디로 리뷰 조회 평점 높은 순 ")
//    @GetMapping("/{itemId}/reviews/HighScore")
//    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getReviewsByItemIdHighScore(@RequestParam(value = "page", defaultValue = "1") int page, @PathVariable Long itemId) {
//        Page<ReviewDto.ReviewResponseDto> reviewPage = reviewService.getReviewsByItemIdHighScore(itemId, page);
//        return ResponseEntity.ok(reviewPage);
//    }
//
//    @Operation(summary="아이템 아이디로 리뷰 조회 평점 낮은 순 ")
//    @GetMapping("/{itemId}/reviews/LowScore")
//    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getReviewsByItemIdLowScore(@RequestParam(value = "page", defaultValue = "1") int page, @PathVariable Long itemId) {
//        Page<ReviewDto.ReviewResponseDto> reviewPage = reviewService.getReviewsByItemIdLowScore(itemId, page);
//        return ResponseEntity.ok(reviewPage);
//    }
//
//    @Operation(summary = "유저 아이디로 리뷰 조회")
//    @GetMapping("/{userId}/reviews")
//    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getReviewsByUserId(@RequestParam(value = "page", defaultValue = "1") int page, @PathVariable Long userId) {
//        Page<ReviewDto.ReviewResponseDto> reviewPage = reviewService.getReviewsByUserId(userId, page);
//        return ResponseEntity.ok(reviewPage);
//    }
//
//    @Operation(summary="유저 아이디로 리뷰 조회 평점 높은 순 ")
//    @GetMapping("/{userId}/reviews/HighScore")
//    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getReviewsByUserIdHighScore(@RequestParam(value = "page", defaultValue = "1") int page, @PathVariable Long userId) {
//        Page<ReviewDto.ReviewResponseDto> reviewPage = reviewService.getReviewsByUserIdHighScore(userId, page);
//        return ResponseEntity.ok(reviewPage);
//    }
//
//    @Operation(summary="유저 아이디로 리뷰 조회 평점 낮은 순 ")
//    @GetMapping("/{userId}/reviews/LowScore")
//    public ResponseEntity<Page<ReviewDto.ReviewResponseDto>> getReviewsByUserIdLowScore(@RequestParam(value = "page", defaultValue = "1") int page, @PathVariable Long userId) {
//        Page<ReviewDto.ReviewResponseDto> reviewPage = reviewService.getReviewsByUserIdLowScore(userId, page);
//        return ResponseEntity.ok(reviewPage);
//    }

    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("성공적으로 리뷰가 삭제되었습니다.");
    }
}

