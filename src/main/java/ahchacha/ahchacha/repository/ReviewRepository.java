package ahchacha.ahchacha.repository;

import ahchacha.ahchacha.domain.Review;
import ahchacha.ahchacha.domain.common.enums.PersonType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    Page<Review> findByPersonType(PersonType personType, Pageable pageable);

    Optional<Review> findByItemId(Long itemId);

    Page<Review> findByUserIdAndPersonType(Long userId, PersonType personType, Pageable pageable);

}
