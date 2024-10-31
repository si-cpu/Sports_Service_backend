package SportsService.backend.repository;

import SportsService.backend.entity.LikeReply;
import SportsService.backend.entity.Reply;
import SportsService.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 댓글 좋아요(LikeReply) 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 작업을 수행할 수 있습니다.
 *
 * @author minus43
 * @since 2024-10-26
 * @see LikeReply
 * @see JpaRepository
 */
public interface LikeReplyRepository extends JpaRepository<LikeReply, Long> {
    /**
     * 특정 사용자가 특정 댓글에 대해 누른 좋아요 정보를 조회합니다.
     * 
     * @param user 조회할 사용자
     * @param reply 조회할 댓글
     * @return 좋아요 정보를 담은 Optional 객체. 좋아요 정보가 없는 경우 Optional.empty() 반환
     */
    Optional<LikeReply> findByUserAndReply(User user, Reply reply);
}
