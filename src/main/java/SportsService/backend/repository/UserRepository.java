package SportsService.backend.repository;

import SportsService.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 메서드가 자동으로 제공됩니다.
 *
 * @author minus43
 * @since 2024-10-23
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickName(String nickName);

    Optional<User> findByEmail(String email);
}

