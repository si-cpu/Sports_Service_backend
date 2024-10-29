package SportsService.backend.repository;

import SportsService.backend.entity.Board;
import SportsService.backend.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Reply 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 메서드가 자동으로 제공됩니다.
 * 추가적인 검색 메서드는 필요에 따라 정의할 수 있습니다.
 *
 * 댓글이 특정 게시글(Board)에 속하는지 등을 기반으로 검색하는 메서드를 추가할 수 있습니다.
 *
 * @see Reply
 * @see Board
 *
 * @since 2024-10-26
 * @author minus43
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByBoard(Board board);
}
