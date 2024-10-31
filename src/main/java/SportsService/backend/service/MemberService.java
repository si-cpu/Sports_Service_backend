package SportsService.backend.service;

import SportsService.backend.dto.request.LoginRequestDto;
import SportsService.backend.dto.request.SignUpRequestDto;
import SportsService.backend.entity.User;
import SportsService.backend.repository.UserRepository;
import SportsService.backend.utils.LoginUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 회원 관련 기능을 처리하는 서비스 클래스입니다.
 * 회원가입, 로그인, 회원정보 수정, 회원탈퇴 등의 기능을 제공합니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>회원가입 및 중복 검사</li>
 *   <li>로그인 인증 및 세션 관리</li>
 *   <li>회원정보 수정</li>
 *   <li>회원탈퇴</li>
 *   <li>로그인 상태 확인</li>
 * </ul>
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
     * @return 회원가입 성공 시 true, 실패 시 false
     */
    public boolean signUp(SignUpRequestDto dto) {
        try {
            User user = dto.toUser(encoder);
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
     * @param nickname 중복 확인할 닉네임
     * @return 중복된 닉네임이 존재하면 true, 없으면 false
     */
    public boolean isValidNickname(String nickname) {
        try {
            Optional<User> user = userRepository.findByNickName(nickname);
            return user.isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 이메일 중복 여부를 확인하는 메서드입니다.
     * 데이터베이스에서 해당 이메일을 가진 사용자가 있는지 확인합니다.
     *
     * @param email 중복 확인할 이메일
     * @return 중복된 이메일이 존재하면 true, 없으면 false
     */
    public boolean isValidEmail(String email) {
        try {
            Optional<User> user = userRepository.findByEmail(email);
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

    /**
     * 로그인 시 세션 및 쿠키를 설정하는 메서드입니다.
     * 세션에 사용자 정보를 저장하고, 쿠키를 설정하여 자동 로그인 여부에 따라 유지 기간을 설정합니다.
     *
     * @param dto 로그인 요청 데이터를 담은 DTO 객체
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 쿠키 설정 성공 시 true, 실패 시 false
     */
    public boolean makeCookie(LoginRequestDto dto, HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            session.setAttribute("member", dto.getNickName());

            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            if ("true".equals(dto.getAutoLogin())) {
                cookie.setMaxAge(60 * 60 * 24 * 7);  // 7일 동안 유지
            } else {
                cookie.setMaxAge(-1);  // 세션 쿠키 설정
            }

            response.addCookie(cookie);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 로그아웃 시 세션을 무효화하고 JSESSIONID 쿠키를 삭제하는 메서드입니다.
     *
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 쿠키 삭제 성공 시 true, 실패 시 false
     */
    public boolean deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

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

    /**
     * 사용자가 로그인 상태인지 확인하는 메서드입니다.
     * 로그인 유틸리티를 사용하여 로그인 여부를 확인하고, 결과를 맵 형식으로 반환합니다.
     *
     * @param request HTTP 요청 객체
     * @return 로그인 상태를 나타내는 맵, 로그인된 경우 닉네임을 포함
     */
    public Map<String, String> checkLogin(HttpServletRequest request) {
        try {
            String isLogin = LoginUtils.isLogin(request);
            User user = userRepository.findByNickName(isLogin).orElseThrow();
            Map<String, String> map = new HashMap<>();
            map.put("nick_name", user.getNickName());
            map.put("email", user.getEmail());
            map.put("mlb_team",user.getMlbTeam());
            map.put("kbo_team",user.getKboTeam());
            map.put("kl_team",user.getKlTeam());
            map.put("pl_team",user.getPlTeam());
            map.put("kbl_team",user.getKblTeam());
            map.put("nba_team",user.getNbaTeam());
            map.put("vman_team",user.getVmanTeam());
            map.put("vwo_team",user.getVwoTeam());
            return map;
        } catch (Exception e) {
            return null;
        }

    }

    public boolean modifyMember(SignUpRequestDto dto, HttpServletRequest request, HttpServletResponse response) {
        try {

            String isLogin = LoginUtils.isLogin(request);
            System.out.println(isLogin);
            User user = userRepository.findByNickName(isLogin).orElseThrow();
            user.setNickName(dto.getNickName().isBlank() ? user.getNickName() : dto.getNickName());
            user.setPassword(dto.getPassword().isBlank() ? user.getPassword() : encoder.encode(dto.getPassword()));
            user.setEmail(dto.getEmail().isBlank() ? user.getEmail() : dto.getEmail());
            user.setMlbTeam(dto.getMlbTeam().equals(user.getMlbTeam()) ? user.getMlbTeam() : dto.getMlbTeam());
            user.setKboTeam(dto.getKboTeam().equals(user.getKboTeam()) ? user.getKboTeam() : dto.getKboTeam());
            user.setKlTeam(dto.getKlTeam().equals(user.getKlTeam()) ? user.getKlTeam() : dto.getKlTeam());
            user.setPlTeam(dto.getPlTeam().equals(user.getPlTeam()) ? user.getPlTeam() : dto.getPlTeam());
            user.setKblTeam(dto.getKblTeam().equals(user.getKblTeam()) ? user.getKblTeam() : dto.getKblTeam());
            user.setNbaTeam(dto.getNbaTeam().equals(user.getNbaTeam()) ? user.getNbaTeam() : dto.getNbaTeam());
            user.setVmanTeam(dto.getVmanTeam().equals(user.getVmanTeam()) ? user.getVmanTeam() : dto.getVmanTeam());
            user.setVwoTeam(dto.getVwoTeam().equals(user.getVwoTeam()) ? user.getVwoTeam() : dto.getVwoTeam());
            deleteCookie(request, response);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean isValidPassword(String password, HttpServletRequest request) {
        try {
            String isLogin=LoginUtils.isLogin(request);
            System.out.println(isLogin);
            User user=userRepository.findByNickName(isLogin).orElseThrow();
            return encoder.matches(password, user.getPassword());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteMember(HttpServletRequest request, HttpServletResponse response) {
        try {
            String isLogin = LoginUtils.isLogin(request);
            userRepository.delete(userRepository.findByNickName(isLogin).orElseThrow());
            deleteCookie(request, response);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
