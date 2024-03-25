package ahchacha.ahchacha.service;

import ahchacha.ahchacha.domain.Item;
import ahchacha.ahchacha.domain.Review;
import ahchacha.ahchacha.domain.User;
import ahchacha.ahchacha.domain.common.enums.PersonType;
import ahchacha.ahchacha.dto.ReviewDto;
import ahchacha.ahchacha.repository.ItemRepository;
import ahchacha.ahchacha.repository.ReviewRepository;
import ahchacha.ahchacha.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewDto.ReviewResponseDto createReview(ReviewDto.ReviewRequestDto reviewDto, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Optional<Item> item = itemRepository.findById(reviewDto.getItemId());

        Review review = Review.builder()
                .user(user)
                .item(item.orElseThrow(() -> new RuntimeException("Item not found")))
                .reviewComment(reviewDto.getReviewComment())
                .reviewScore(reviewDto.getReviewScore())
                .personType(reviewDto.getPersonType())
                .build();

        Review createdReview = reviewRepository.save(review);
        return ReviewDto.ReviewResponseDto.toDto(createdReview);
    }

    public Page<ReviewDto.ReviewResponseDto> getAllReviewsRENTER(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<Review> reviewPage = reviewRepository.findByPersonType(PersonType.RENTER, pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }

    public Page<ReviewDto.ReviewResponseDto> getAllReviewsRECEIVER(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<Review> reviewPage = reviewRepository.findByPersonType(PersonType.RECEIVER, pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }

    public ReviewDto.ReviewResponseDto getReviewByItemId(Long itemId) {
        Review review = reviewRepository.findByItemId(itemId).orElseThrow(() -> new EntityNotFoundException("Review not found for item: " + itemId));
        return ReviewDto.ReviewResponseDto.toDto(review);
    }


//    public Page<ReviewDto.ReviewResponseDto> getReviewsByItemIdHighScore(Long id, int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("createdAt"));
//        sorts.add(Sort.Order.desc("reviewScore"));
//
//        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
//        Page<Review> reviewPage = reviewRepository.findByItemId(id, pageable);
//
//        return ReviewDto.toDtoPage(reviewPage);
//    }
//    public Page<ReviewDto.ReviewResponseDto> getReviewsByItemIdLowScore(Long id, int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.asc("createdAt"));
//        sorts.add(Sort.Order.desc("reviewScore"));
//
//        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
//        Page<Review> reviewPage = reviewRepository.findByItemId(id, pageable);
//
//        return ReviewDto.toDtoPage(reviewPage);
//    }

//    public Page<ReviewDto.ReviewResponseDto> getReviewsByUserId(Long id, int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("createdAt"));
//
//        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
//        Page<Review> reviewPage = reviewRepository.findByUserId(id, pageable);
//
//        return ReviewDto.toDtoPage(reviewPage);
//    }
//
//    public Page<ReviewDto.ReviewResponseDto> getReviewsByUserIdHighScore(Long id, int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("createdAt"));
//        sorts.add(Sort.Order.desc("reviewScore"));
//
//        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
//        Page<Review> reviewPage = reviewRepository.findByUserId(id, pageable);
//
//        return ReviewDto.toDtoPage(reviewPage);
//    }
//    public Page<ReviewDto.ReviewResponseDto> getReviewsByUserIdLowScore(Long id, int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.asc("createdAt"));
//        sorts.add(Sort.Order.desc("reviewScore"));
//
//        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
//        Page<Review> reviewPage = reviewRepository.findByUserId(id, pageable);
//
//        return ReviewDto.toDtoPage(reviewPage);
//    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("Invalid review Id:"));

        reviewRepository.delete(review);
    }
}
