package ahchacha.ahchacha.repository;

import ahchacha.ahchacha.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
