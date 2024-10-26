package SportsService.backend.repository;

import SportsService.backend.entity.Reply;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
@SpringBootTest
@Transactional
@Rollback(false)
class ReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;

    @Test
    void modifyTest(){
        Reply reply = replyRepository.findById(1L).orElseThrow();
        reply.setContent("ddddd");
    }
}
