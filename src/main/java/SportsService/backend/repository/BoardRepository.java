package SportsService.backend.repository;

import SportsService.backend.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Board 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 메서드가 자동으로 제공됩니다.
 * 추가적인 검색 메서드는 필요에 따라 정의할 수 있습니다.
 *
 * @author minus43
 * @since 2024-10-26
 */
public interface BoardRepository extends JpaRepository<Board, Long> {

}
