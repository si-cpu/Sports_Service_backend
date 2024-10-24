package SportsService.backend.service;

import SportsService.backend.dto.request.LoginRequestDto;
import SportsService.backend.dto.request.SignUpRequestDto;
import SportsService.backend.entity.User;
import SportsService.backend.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 회원 관련 서비스 클래스입니다.
 * 회원가입 로직을 처리하고, 사용자의 정보를 데이터베이스에 저장합니다.
 * 또한, 로그인 시 사용자의 인증을 수행합니다.
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
    public boolean signUp(SignUpRequestDto dto) {
        try {
            // SignUpRequestDto를 User 엔티티로 변환하고 비밀번호 암호화
            User user = dto.toUser(encoder);
            // 데이터베이스에 사용자 정보 저장
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 닉네임 중복 여부를 확인하는 메서드입니다.
     * 데이터베이스에서 해당 닉네임을 가진 사용자가 있는지 확인합니다.
     *
     * @param dto 닉네임을 담은 DTO 객체
     * @return 닉네임이 중복되면 true, 중복되지 않으면 false
     */
    public boolean isValidNickname(SignUpRequestDto dto) {
        try {
            Optional<User> user = userRepository.findByNickName(dto.getNickName());
            return user.isPresent();
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 이메일 중복 여부를 확인하는 메서드입니다.
     * 데이터베이스에서 해당 이메일을 가진 사용자가 있는지 확인합니다.
     *
     * @param dto 이메일을 담은 DTO 객체
     * @return 이메일이 중복되면 true, 중복되지 않으면 false
     */
    public boolean isValidEmail(SignUpRequestDto dto) {
        try{
            Optional<User> user = userRepository.findByEmail(dto.getEmail());
            return user.isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 사용자의 로그인 인증을 처리하는 메서드입니다.
     * 닉네임으로 사용자를 찾고, 입력된 비밀번호와 저장된 암호화된 비밀번호를 비교하여 일치 여부를 확인합니다.
     *
     * @param dto 로그인 요청 데이터를 담은 DTO 객체
     * @return 인증 성공 시 true, 실패 시 false
     */
    public boolean authenticate(LoginRequestDto dto) {
        Optional<User> foundUser = userRepository.findByNickName(dto.getNickName());
        return foundUser.isPresent() && encoder.matches(dto.getPassword(), foundUser.get().getPassword());
    }

    public boolean makeCookie(LoginRequestDto dto, HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            session.setAttribute("member", dto.getNickName());  // 세션에 사용자 정보 저장

            // 세션 ID를 쿠키로 설정 (JSESSIONID)
            Cookie cookie = new Cookie("JSESSIONID", session.getId());  // 세션 ID 쿠키
            cookie.setHttpOnly(true);  // 자바스크립트에서 접근 불가능하게 설정
            cookie.setPath("/");       // 모든 경로에서 유효

            // 자동 로그인 설정 여부에 따라 Max-Age 설정
            if (dto.getAutoLogin().equals("true")) {
                cookie.setMaxAge(60 * 60 * 24 * 7);  // 7일 동안 유지 (자동 로그인)
            } else {
                cookie.setMaxAge(-1);  // 세션 쿠키로 설정 (브라우저 닫으면 만료)
            }

            response.addCookie(cookie);  // 쿠키 추가
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();  // 세션 무효화
            }

            // JSESSIONID 쿠키 삭제
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);  // 쿠키 즉시 만료
            cookie.setPath("/");
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
