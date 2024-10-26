package SportsService.backend.repository;

import SportsService.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 메서드가 자동으로 제공됩니다.
 * 추가로 사용자 닉네임과 이메일을 기반으로 검색하는 메서드가 정의되어 있습니다.
 *
 * @author minus43
 * @since 2024-10-23
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 닉네임을 기반으로 사용자를 검색합니다.
     *
     * @param nickName 검색할 사용자의 닉네임
     * @return 닉네임에 해당하는 사용자가 존재할 경우 User 객체를 담은 Optional
     */
    Optional<User> findByNickName(String nickName);

    /**
     * 이메일을 기반으로 사용자를 검색합니다.
     *
     * @param email 검색할 사용자의 이메일
     * @return 이메일에 해당하는 사용자가 존재할 경우 User 객체를 담은 Optional
     */
    Optional<User> findByEmail(String email);
}
