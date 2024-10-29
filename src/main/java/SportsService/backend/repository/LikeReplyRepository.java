package SportsService.backend.repository;

import SportsService.backend.entity.LikeReply;
import SportsService.backend.entity.Reply;
import SportsService.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeReplyRepository extends JpaRepository<LikeReply, Long> {
    Optional<LikeReply> findByUserAndReply(User user, Reply reply);
}
