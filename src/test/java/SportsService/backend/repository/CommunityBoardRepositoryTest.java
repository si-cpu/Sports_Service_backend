package SportsService.backend.repository;

import SportsService.backend.dto.response.CommunityBoardListResponceDTO;
import SportsService.backend.entity.CommunityBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
@Rollback(false)
class CommunityBoardRepositoryTest {

    @Autowired
    CommunityBoardRepository communityBoardRepository;

    @Test
    void test1() {
        for(long i = 0; i<10; i++){
            CommunityBoard test = CommunityBoard.builder()
                    .userKey(i)
                    .title("1")
                    .content("2")
                    .viewCount(6L)
                    .goodBoard(7L)
                    .build();
            communityBoardRepository.save(test);
        }
    }

    @Test
    void test2() {

        List<CommunityBoard> communityBoardList = communityBoardRepository.findAll();
        communityBoardList.forEach(communityBoard -> System.out.println(communityBoard.toString()));
    }
}
