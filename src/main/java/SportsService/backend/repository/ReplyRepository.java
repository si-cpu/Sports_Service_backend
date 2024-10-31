package SportsService.backend.repository;

import SportsService.backend.entity.Board;
import SportsService.backend.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 댓글(Reply) 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 작업을 수행할 수 있습니다.
 *
 * <p>제공되는 기본 메서드:</p>
 * <ul>
 *   <li>save(Reply entity): 댓글 저장 및 수정</li>
 *   <li>findById(Long id): ID로 댓글 조회</li>
 *   <li>findAll(): 모든 댓글 조회</li>
 *   <li>delete(Reply entity): 댓글 삭제</li>
 *   <li>count(): 전체 댓글 수 조회</li>
 * </ul>
 *
 * @author minus43
 * @since 2024-10-26
 * @see Reply
 * @see Board
 * @see JpaRepository
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    /**
     * 특정 게시글에 달린 모든 댓글을 조회합니다.
     * 
     * @param board 댓글을 조회할 게시글
     * @return 해당 게시글에 달린 모든 댓글 목록
     */
    List<Reply> findByBoard(Board board);
}
