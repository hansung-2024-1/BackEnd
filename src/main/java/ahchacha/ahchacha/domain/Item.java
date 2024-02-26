package ahchacha.ahchacha.domain;

import ahchacha.ahchacha.domain.common.BaseEntity;
import ahchacha.ahchacha.domain.common.enums.PersonOrOfficial;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; //제목

    @Column(name = "first_price")
    private int firstPrice; //보증금

    @Column(name = "price")
    private int price; //대여비

    @Column(nullable = false)
    private LocalDateTime canBorrowDateTime; //대여가능 날짜,시간

    @Column(nullable = false)
    private LocalDateTime returnDateTime; // 반납 날짜, 시간

    @Column(nullable = false)
    private String borrowPlace; //대여 장소

    @Column(nullable = false)
    private String returnPlace; //반납 장소

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'PERSON'")
    private PersonOrOfficial personOrOfficial; // 개인 OR 학생회

    @ElementCollection
    @CollectionTable(name = "item_images", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "image_url")
    private List<String> imageUrls; //아이템 이미지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
    private ItemReview itemReview;
}
