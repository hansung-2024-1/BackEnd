package ahchacha.ahchacha.repository;

import ahchacha.ahchacha.domain.Review;
import ahchacha.ahchacha.domain.common.enums.PersonType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    Page<Review> findByPersonType(PersonType personType, Pageable pageable);

    Page<Review> findByItemId(Long id, Pageable pageable);
    Page<Review> findByUserId(Long id, Pageable pageable);
}
