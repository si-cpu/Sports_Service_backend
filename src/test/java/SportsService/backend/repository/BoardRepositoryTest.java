package SportsService.backend.repository;

import SportsService.backend.entity.Board;
import SportsService.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(false)
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void saveTest(){
        Optional<User> user = userRepository.findById(1L);
        System.out.println("user = " + user);
        if(user.isPresent()){
            Board board = Board.builder()
                    .user(user.get())
                    .title("saveTest")
                    .content("saveTest")
                    .build();
            boardRepository.save(board);
        }

    }

    @Test
    void deleteuserTest(){
        userRepository.deleteById(1L);
    }

}