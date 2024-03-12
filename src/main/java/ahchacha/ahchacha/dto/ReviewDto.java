package ahchacha.ahchacha.dto;

import ahchacha.ahchacha.domain.Item;
import ahchacha.ahchacha.domain.ItemReview;
import ahchacha.ahchacha.domain.common.enums.Category;
import ahchacha.ahchacha.domain.common.enums.PersonOrOfficial;
import ahchacha.ahchacha.domain.common.enums.PersonType;
import ahchacha.ahchacha.domain.common.enums.Reservation;
import lombok.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewDto {
    @Getter
    @Setter
    @Builder
    public static class ReviewRequestDto {
        private Long userId;
        private Long itemId;
        private String reviewComment;
        private BigDecimal reviewScore;
        private PersonType personType;
    }

    @Getter
    @Setter
    @Builder
    public static class ReviewResponseDto {
        private Long reviewId;
        private Long userId;
        private Long itemId;
        private String reviewComment;
        private BigDecimal reviewScore;
        private PersonType personType;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static ReviewResponseDto toDto(ItemReview itemReview) {
            return ReviewResponseDto.builder()
                    .userId(itemReview.getId())
                    .itemId(itemReview.getItem().getId())
                    .userId(itemReview.getUser().getId())
                    .reviewComment(itemReview.getReviewComment())
                    .reviewScore(itemReview.getReviewScore())
                    .personType(itemReview.getPersonType())
                    .createdAt(itemReview.getCreatedAt())
                    .updatedAt(itemReview.getUpdatedAt())
                    .build();
        }
    }

    public static Page<ReviewResponseDto> toDtoPage(Page<ItemReview> itemReviewPage) {
        return itemReviewPage.map(ReviewResponseDto::toDto);
    }
}
