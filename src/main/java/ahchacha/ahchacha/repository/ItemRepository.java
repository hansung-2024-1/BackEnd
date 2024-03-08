package ahchacha.ahchacha.repository;

import ahchacha.ahchacha.domain.Item;
import ahchacha.ahchacha.domain.common.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAll(Pageable pageable);

    Page<Item> findByTitleContaining(String title, Pageable pageable);

    Page<Item> findByCategory(Category category, Pageable pageable);

//    Page<Item> findByTitleContainingOrCategory(String title, Category category, Pageable pageable);
}