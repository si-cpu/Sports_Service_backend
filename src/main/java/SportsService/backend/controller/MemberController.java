package SportsService.backend.controller;

import SportsService.backend.dto.request.LoginRequestDto;
import SportsService.backend.dto.request.SignUpRequestDto;
import SportsService.backend.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 회원가입과 관련된 요청을 처리하는 컨트롤러 클래스입니다.
 * 클라이언트로부터 회원가입 데이터를 받아 서비스 계층에 전달하고,
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

    @PostMapping("/valid_id")
    public ResponseEntity<String> validNickname(@RequestBody SignUpRequestDto dto) {
        if(memberService.isValidNickname(dto)){
            return ResponseEntity.badRequest().body("중복");
        }
        return ResponseEntity.ok().body("가능");
    }

    @PostMapping("/valid_email")
    public ResponseEntity<String> validEmail(@RequestBody SignUpRequestDto dto) {
        if(memberService.isValidEmail(dto)){
            return ResponseEntity.badRequest().body("중복");
        }
        return ResponseEntity.ok().body("가능");
    }

    @PostMapping("/login")
    public ResponseEntity<Map> login(@RequestBody LoginRequestDto dto, HttpServletRequest request) {
        HttpSession session=memberService.authenticate(dto, request);
        if(session == null){
            Map<String,String> response = new HashMap<>();
            response.put("message","로그인 실패");
            response.put("seesion_id", "failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        Map<String,String> response = new HashMap<>();
        response.put("message","로그인 성공");
        response.put("seesion_id", session.getId());
        return ResponseEntity.ok(response);

    }
}
