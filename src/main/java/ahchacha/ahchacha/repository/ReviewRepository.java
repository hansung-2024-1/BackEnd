package ahchacha.ahchacha.repository;

import ahchacha.ahchacha.domain.ItemReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ItemReview,Long> {


    Page<ItemReview> findByItemId(Long id, Pageable pageable);
    Page<ItemReview> findByUserId(Long id, Pageable pageable);
}
