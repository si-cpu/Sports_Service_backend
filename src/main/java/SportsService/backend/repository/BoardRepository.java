package SportsService.backend.repository;

import SportsService.backend.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 게시글(Board) 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 작업을 수행할 수 있습니다.
 *
 * <p>제공되는 기본 메서드:</p>
 * <ul>
 *   <li>save(Board entity): 게시글 저장 및 수정</li>
 *   <li>findById(Long id): ID로 게시글 조회</li>
 *   <li>findAll(): 모든 게시글 조회</li>
 *   <li>delete(Board entity): 게시글 삭제</li>
 *   <li>count(): 전체 게시글 수 조회</li>
 * </ul>
 *
 * @author minus43
 * @since 2024-10-26
 * @see Board
 * @see JpaRepository
 */
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 필요한 경우 추가적인 쿼리 메서드를 정의할 수 있습니다.
}
