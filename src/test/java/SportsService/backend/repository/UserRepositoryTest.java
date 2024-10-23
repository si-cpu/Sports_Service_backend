package SportsService.backend.repository;

import SportsService.backend.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Test
    @DisplayName("")
    void test1() {
        // given
        User user = User.builder()
                .email("test")
                .password("test")
                .auth("test")
                .nickName("test")
                .build();
        userRepository.save(user);

        Optional<User> user1 = userRepository.findById(1L);

        assertTrue(user1.isPresent());

        System.out.println(user1.get());
        // when

        // then
    }
  
}