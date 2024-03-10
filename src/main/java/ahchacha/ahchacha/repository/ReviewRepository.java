package ahchacha.ahchacha.repository;

import ahchacha.ahchacha.domain.ItemReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ItemReview,Long> {

    Page<ItemReview> findByItemIdAndHighScore(Pageable pageable);
    Page<ItemReview> findByItemIdAndLowScore(Pageable pageable);
    Page<ItemReview> findByUserIdAndHighScore(Pageable pageable);
    Page<ItemReview> findByUserIdAndLowScore(Pageable pageable);
    Page<ItemReview> findByItemId(Pageable pageable);
    Page<ItemReview> findByUserId(Pageable pageable);
}
