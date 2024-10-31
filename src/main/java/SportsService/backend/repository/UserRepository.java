package SportsService.backend.repository;

import SportsService.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 사용자(User) 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 작업을 수행할 수 있습니다.
 *
 * <p>제공되는 기본 메서드:</p>
 * <ul>
 *   <li>save(User entity): 사용자 정보 저장 및 수정</li>
 *   <li>findById(Long id): ID로 사용자 조회</li>
 *   <li>findAll(): 모든 사용자 조회</li>
 *   <li>delete(User entity): 사용자 정보 삭제</li>
 *   <li>count(): 전체 사용자 수 조회</li>
 * </ul>
 *
 * @author minus43
 * @since 2024-10-23
 * @see User
 * @see JpaRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 닉네임을 기반으로 사용자를 검색합니다.
     * 닉네임은 사용자를 식별하는 고유한 값이므로, 중복된 닉네임은 존재하지 않습니다.
     *
     * @param nickName 검색할 사용자의 닉네임
     * @return 닉네임에 해당하는 사용자가 존재할 경우 User 객체를 담은 Optional
     */
    Optional<User> findByNickName(String nickName);

    /**
     * 이메일을 기반으로 사용자를 검색합니다.
     * 이메일은 사용자를 식별하는 고유한 값이므로, 중복된 이메일은 존재하지 않습니다.
     *
     * @param email 검색할 사용자의 이메일
     * @return 이메일에 해당하는 사용자가 존재할 경우 User 객체를 담은 Optional
     */
    Optional<User> findByEmail(String email);
}
