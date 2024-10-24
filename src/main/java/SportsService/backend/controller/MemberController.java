package SportsService.backend.controller;

import SportsService.backend.dto.request.LoginRequestDto;
import SportsService.backend.dto.request.SignUpRequestDto;
import SportsService.backend.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회원가입과 로그인 관련된 요청을 처리하는 컨트롤러 클래스입니다.
 * 클라이언트로부터 회원가입, 로그인 등의 데이터를 받아 서비스 계층에 전달하고,
 * 처리 결과를 응답으로 반환합니다.
 *
 * @author minus43
 * @since 2024-10-23
 */
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    /**
     * 회원 관련 로직을 처리하는 서비스 객체입니다.
     */
    private final MemberService memberService;

    /**
     * 회원가입 요청을 처리하는 메서드입니다.
     * 클라이언트로부터 전달된 회원가입 데이터를 받아 회원가입 로직을 수행하고,
     * 성공 메시지를 반환합니다.
     *
     * @param dto 회원가입 요청 데이터를 담은 DTO 객체
     * @return 성공 메시지를 담은 ResponseEntity 객체
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignUpRequestDto dto) {
        memberService.register(dto);
        return ResponseEntity.ok().body("성공");
    }

    /**
     * 닉네임 중복 여부를 확인하는 메서드입니다.
     * 클라이언트로부터 전달된 닉네임을 이용하여 중복 여부를 확인하고,
     * 중복된 경우 "중복", 사용 가능한 경우 "가능" 메시지를 반환합니다.
     *
     * @param dto 닉네임을 담은 DTO 객체
     * @return 중복 여부를 담은 ResponseEntity 객체
     */
    @PostMapping("/valid_id")
    public ResponseEntity<String> validNickname(@RequestBody SignUpRequestDto dto) {
        if (memberService.isValidNickname(dto)) {
            return ResponseEntity.badRequest().body("중복");
        }
        return ResponseEntity.ok().body("가능");
    }

    /**
     * 이메일 중복 여부를 확인하는 메서드입니다.
     * 클라이언트로부터 전달된 이메일을 이용하여 중복 여부를 확인하고,
     * 중복된 경우 "중복", 사용 가능한 경우 "가능" 메시지를 반환합니다.
     *
     * @param dto 이메일을 담은 DTO 객체
     * @return 중복 여부를 담은 ResponseEntity 객체
     */
    @PostMapping("/valid_email")
    public ResponseEntity<String> validEmail(@RequestBody SignUpRequestDto dto) {
        if (memberService.isValidEmail(dto)) {
            return ResponseEntity.badRequest().body("중복");
        }
        return ResponseEntity.ok().body("가능");
    }

    /**
     * 로그인 요청을 처리하는 메서드입니다.
     * 클라이언트로부터 전달된 로그인 데이터를 받아 인증을 수행하고,
     * 인증 성공 시 세션과 쿠키를 설정하여 로그인 상태를 유지합니다.
     *
     * @param dto 로그인 요청 데이터를 담은 DTO 객체
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 로그인 결과 메시지를 담은 ResponseEntity 객체
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto, HttpServletRequest request, HttpServletResponse response) {
        if (!memberService.authenticate(dto)) {
            return ResponseEntity.badRequest().body("거부");
        } else {
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
            return ResponseEntity.ok().body(dto.getAutoLogin().equals("true") ? "자동 로그인 성공" : "로그인 성공");
        }
    }

    /**
     * 로그인 상태를 확인하는 메서드입니다.
     * 현재 세션에 로그인된 사용자가 있는지 확인하고,
     * 로그인 상태인 경우 "현재 로그인 상태입니다." 메시지를 반환합니다.
     *
     * @param request HTTP 요청 객체
     * @return 로그인 상태 메시지를 담은 ResponseEntity 객체
     */
    @GetMapping("/check_login")
    public ResponseEntity<String> checkLoginStatus(HttpServletRequest request) {
        HttpSession session = request.getSession(false);  // 세션 확인
        if (session != null && session.getAttribute("member") != null) {
            return ResponseEntity.ok("현재 로그인 상태입니다.");
        } else {
            return ResponseEntity.badRequest().body("로그인되지 않았습니다.");
        }
    }

    /**
     * 로그아웃 요청을 처리하는 메서드입니다.
     * 현재 세션을 무효화하고, 세션 ID 쿠키를 만료시켜 로그아웃을 수행합니다.
     *
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 로그아웃 성공 메시지를 담은 ResponseEntity 객체
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
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

        return ResponseEntity.ok("로그아웃 성공");
    }
}
