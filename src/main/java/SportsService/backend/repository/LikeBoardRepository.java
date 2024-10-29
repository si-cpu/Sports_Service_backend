package SportsService.backend.repository;

import SportsService.backend.entity.Board;
import SportsService.backend.entity.LikeBoard;
import SportsService.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {
    Optional<LikeBoard> findByUserAndBoard(User user, Board board);
}
