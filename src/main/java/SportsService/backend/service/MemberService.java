package SportsService.backend.service;


import SportsService.backend.dto.request.SignUpRequestDto;
import SportsService.backend.entity.User;
import SportsService.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    String savePath="임시";


    public void register(SignUpRequestDto dto) {
        User user = dto.toUser(encoder, savePath);
        System.out.println("user.toString() = " + user.toString());
        userRepository.save(user);
    }
}
