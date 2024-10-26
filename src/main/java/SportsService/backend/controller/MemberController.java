package SportsService.backend.controller;

import SportsService.backend.dto.request.LoginRequestDto;
import SportsService.backend.dto.request.SignUpRequestDto;
import SportsService.backend.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 회원가입과 로그인 관련된 요청을 처리하는 컨트롤러 클래스입니다.
 * 클라이언트로부터 회원가입, 로그인 등의 데이터를 받아 서비스 계층에 전달하고,
 * 처리 결과를 응답으로 반환합니다.
 *
 * @since 2024-10-23
 * @author minus43
 * @see MemberService
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
     * 성공 또는 실패 메시지를 반환합니다.
     *
     * @param dto 회원가입 요청 데이터를 담은 DTO 객체
     * @return 성공 메시지를 담은 ResponseEntity 객체, 실패 시 실패 메시지를 반환
     */
    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody SignUpRequestDto dto) {
        if (memberService.signUp(dto)) {
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.badRequest().body("failed");
    }

    /**
     * 닉네임 중복 여부를 확인하는 메서드입니다.
     * 클라이언트로부터 전달된 닉네임을 이용하여 중복 여부를 확인하고,
     * 중복된 경우 "failed", 사용 가능한 경우 "success" 메시지를 반환합니다.
     *
     * @param dto 닉네임을 담은 DTO 객체
     * @return 중복 여부를 담은 ResponseEntity 객체
     */
    @PostMapping("/valid_id")
    public ResponseEntity<String> validNickname(@RequestBody SignUpRequestDto dto) {
        if (memberService.isValidNickname(dto)) {
            return ResponseEntity.badRequest().body("failed");
        }
        return ResponseEntity.ok().body("success");
    }

    /**
     * 이메일 중복 여부를 확인하는 메서드입니다.
     * 클라이언트로부터 전달된 이메일을 이용하여 중복 여부를 확인하고,
     * 중복된 경우 "failed", 사용 가능한 경우 "success" 메시지를 반환합니다.
     *
     * @param dto 이메일을 담은 DTO 객체
     * @return 중복 여부를 담은 ResponseEntity 객체
     */
    @PostMapping("/valid_email")
    public ResponseEntity<String> validEmail(@RequestBody SignUpRequestDto dto) {
        if (memberService.isValidEmail(dto)) {
            return ResponseEntity.badRequest().body("failed");
        }
        return ResponseEntity.ok().body("success");
    }

    /**
     * 로그인 요청을 처리하는 메서드입니다.
     * 클라이언트로부터 전달된 로그인 데이터를 받아 인증을 수행하고,
     * 인증 성공 시 세션과 쿠키를 설정하여 로그인 상태를 유지합니다.
     *
     * @param dto 로그인 요청 데이터를 담은 DTO 객체
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 로그인 성공 시 "success" 메시지를 반환하고, 실패 시 "failed" 메시지를 반환
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto, HttpServletRequest request, HttpServletResponse response) {
        if (memberService.authenticate(dto)) {
            if (memberService.makeCookie(dto, request, response)) {
                return ResponseEntity.ok().body("success");
            }
        }
        return ResponseEntity.badRequest().body("failed");
    }

    /**
     * 로그아웃 요청을 처리하는 메서드입니다.
     * 현재 세션을 무효화하고, 세션 ID 쿠키를 만료시켜 로그아웃을 수행합니다.
     *
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 로그아웃 성공 시 "success" 메시지를 반환하고, 실패 시 "failed" 메시지를 반환
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        if (memberService.deleteCookie(request, response)) {
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.badRequest().body("failed");
    }

    /**
     * 로그인 상태를 확인하는 메서드입니다.
     * 현재 사용자가 로그인 상태인지 확인하고, 로그인된 경우 닉네임을 포함한 정보를 반환합니다.
     *
     * @param request HTTP 요청 객체
     * @return 로그인 상태를 담은 ResponseEntity 객체, 실패 시 "failed" 메시지를 반환
     */
    @GetMapping("/check_login")
    public ResponseEntity<?> checkLogin(HttpServletRequest request) {
        Map<String, String> map = memberService.checkLogin(request);
        if (map != null) {
            return ResponseEntity.ok().body(map);
        }
        return ResponseEntity.badRequest().body("failed");
    }
}
