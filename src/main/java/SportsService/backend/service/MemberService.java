package SportsService.backend.service;

import SportsService.backend.dto.request.LoginRequestDto;
import SportsService.backend.dto.request.SignUpRequestDto;
import SportsService.backend.entity.User;
import SportsService.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 회원 관련 서비스 클래스입니다.
 * 회원가입 로직을 처리하고, 사용자의 정보를 데이터베이스에 저장합니다.
 *
 * @author minus43
 * @since 2024-10-23
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    /**
     * 사용자 정보를 처리하기 위한 UserRepository 객체입니다.
     */
    private final UserRepository userRepository;

    /**
     * 비밀번호 암호화를 처리하기 위한 PasswordEncoder 객체입니다.
     */
    private final PasswordEncoder encoder;

    /**
     * 회원가입을 처리하는 메서드입니다.
     * SignUpRequestDto로부터 전달된 정보를 바탕으로 User 객체를 생성하고,
     * 비밀번호를 암호화하여 데이터베이스에 저장합니다.
     *
     * @param dto 회원가입 요청 데이터를 담은 DTO 객체
     */
    public void register(SignUpRequestDto dto) {
        // SignUpRequestDto를 User 엔티티로 변환하고 비밀번호 암호화
        User user = dto.toUser(encoder);
        // 데이터베이스에 사용자 정보 저장
        userRepository.save(user);
    }

    public boolean isValidNickname(SignUpRequestDto dto) {
        Optional<User> user = userRepository.findByNickName(dto.getNickName());
        return user.isPresent();
    }

    public boolean isValidEmail(SignUpRequestDto dto) {
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        return user.isPresent();
    }

    public HttpSession authenticate(LoginRequestDto dto, HttpServletRequest request) {
        Optional<User> foundUser= userRepository.findByNickName(dto.getNickName());
        boolean userValid=foundUser.filter(user -> encoder.matches(dto.getPassword(), user.getPassword())).isPresent();
        if(userValid){
            HttpSession session =request.getSession(true);
            session.setAttribute("user", foundUser);
            return session;
        }
        return null;
    }
}

