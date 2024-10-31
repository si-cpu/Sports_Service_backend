package SportsService.backend.repository;

import SportsService.backend.entity.Board;
import SportsService.backend.entity.LikeBoard;
import SportsService.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 게시글 좋아요(LikeBoard) 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 작업을 수행할 수 있습니다.
 *
 * @author minus43
 * @since 2024-10-26
 * @see LikeBoard
 * @see JpaRepository
 */
public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {
    /**
     * 특정 사용자가 특정 게시글에 대해 누른 좋아요 정보를 조회합니다.
     * 
     * @param user 조회할 사용자
     * @param board 조회할 게시글
     * @return 좋아요 정보를 담은 Optional 객체. 좋아요 정보가 없는 경우 Optional.empty() 반환
     */
    Optional<LikeBoard> findByUserAndBoard(User user, Board board);
}
