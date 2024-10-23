package SportsService.backend.repository;


import SportsService.backend.entity.CommunityBoard;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityBoardRepository
        extends JpaRepository<CommunityBoard, Long> {

}
