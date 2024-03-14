package ahchacha.ahchacha.service;

import ahchacha.ahchacha.domain.Item;
import ahchacha.ahchacha.domain.ItemReview;
import ahchacha.ahchacha.domain.User;
import ahchacha.ahchacha.dto.ReviewDto;
import ahchacha.ahchacha.repository.ItemRepository;
import ahchacha.ahchacha.repository.ReviewRepository;
import ahchacha.ahchacha.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public ItemReview saveReview(Long userId, Long itemId, ReviewDto.ReviewRequestDto reviewDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + itemId));

        ItemReview review = ItemReview.builder()
                .user(user)
                .item(item)
                .reviewComment(reviewDto.getReviewComment())
                .reviewScore(reviewDto.getReviewScore())
                .personType(reviewDto.getPersonType())
                .build();

        return reviewRepository.save(review);
    }

    public Page<ReviewDto.ReviewResponseDto> getAllReviews(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<ItemReview> reviewPage = reviewRepository.findAll(pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }

    public Page<ReviewDto.ReviewResponseDto> getReviewsByItemId(Long id, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<ItemReview> reviewPage = reviewRepository.findByItemId(id, pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }

    public Page<ReviewDto.ReviewResponseDto> getReviewsByItemIdHighScore(Long id, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        sorts.add(Sort.Order.desc("reviewScore"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<ItemReview> reviewPage = reviewRepository.findByItemId(id, pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }
    public Page<ReviewDto.ReviewResponseDto> getReviewsByItemIdLowScore(Long id, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("createdAt"));
        sorts.add(Sort.Order.desc("reviewScore"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<ItemReview> reviewPage = reviewRepository.findByItemId(id, pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }

    public Page<ReviewDto.ReviewResponseDto> getReviewsByUserId(Long id, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<ItemReview> reviewPage = reviewRepository.findByUserId(id, pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }

    public Page<ReviewDto.ReviewResponseDto> getReviewsByUserIdHighScore(Long id, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        sorts.add(Sort.Order.desc("reviewScore"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<ItemReview> reviewPage = reviewRepository.findByUserId(id, pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }
    public Page<ReviewDto.ReviewResponseDto> getReviewsByUserIdLowScore(Long id, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("createdAt"));
        sorts.add(Sort.Order.desc("reviewScore"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<ItemReview> reviewPage = reviewRepository.findByUserId(id, pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        ItemReview review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("Invalid review Id:"));

        reviewRepository.delete(review);
    }


    /*public Page<ReviewDto.ReviewResponseDto> getReviewsByItemIdAndHighScore(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("score"));
        sorts.add(Sort.Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<ItemReview> reviewPage = reviewRepository.findByItemIdAndHighScore(pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }

    public Page<ReviewDto.ReviewResponseDto> getReviewsByItemIdAndLowScore(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("score"));
        sorts.add(Sort.Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<ItemReview> reviewPage = reviewRepository.findByItemIdAndLowScore(pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }

    public Page<ReviewDto.ReviewResponseDto> getReviewsByUserIdAndHighScore(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("score"));
        sorts.add(Sort.Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<ItemReview> reviewPage = reviewRepository.findByUserIdAndHighScore(pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }

    public Page<ReviewDto.ReviewResponseDto> getReviewsByUserIdAndLowScore(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("score"));
        sorts.add(Sort.Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sorts));
        Page<ItemReview> reviewPage = reviewRepository.findByUserIdAndLowScore(pageable);

        return ReviewDto.toDtoPage(reviewPage);
    }*/
}
