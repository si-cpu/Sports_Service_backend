package SportsService.backend.repository;


import SportsService.backend.entity.CommunityBoard;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityBoardRepository
        extends JpaRepository<CommunityBoard, Long> {

    // 조회수 업데이트
    @Modifying
    @Query("UPDATE CommunityBoard c SET c.viewCount = c.viewCount + 1 WHERE c.boardNum = ?1")
    void updateViewCount(long boardNumber);

    //게시물 삭제
    //@Query
    //void delete(long boardNum);

}
