package SportsService.backend.controller;

import SportsService.backend.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

/**
 * 카카오 인증 및 회원가입 처리를 위한 컨트롤러 클래스입니다.
 * 카카오 인증 페이지로의 리디렉션과 인증 코드로 사용자 정보를 가져오는 기능을 제공합니다.
 *
 * @since 2024-10-23
 * @author minus43
 */
@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoController {

    /**
     * 카카오 관련 로직을 처리하는 서비스 객체입니다.
     */
    private final KakaoService kakaoService;

    /**
     * 카카오 인증 요청 URL을 저장합니다.
     */
    private String requestUrl;

    /**
     * 자동 로그인 여부를 저장합니다.
     */
    private String autoLogin;

    /**
     * 카카오 인증 페이지로 리디렉션하는 메서드입니다.
     * 클라이언트가 요청한 리디렉션 URI와 자동 로그인 여부를 설정하고,
     * 카카오 인증 페이지 URL을 반환합니다.
     *
     * @param redirecturi 클라이언트가 요청한 리디렉션 URI
     * @param autologin 자동 로그인 여부
     * @return 카카오 인증 페이지 URL이 담긴 ResponseEntity 객체
     */
    @GetMapping("signup")
    public ResponseEntity<?> redirectToKakao(
            @RequestParam("redirectUri") String redirecturi,
            @RequestParam(value = "auto_login", defaultValue = "false") boolean autologin) {

        requestUrl = redirecturi;
        autoLogin = autologin ? "true" : "false";

        Map<String, String> response = kakaoService.getKakaoAuthPageUrlHeaders();

        if (response != null) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body("failed");
    }

    /**
     * 카카오 인증 코드로 사용자 정보를 가져와 로그인 또는 회원가입을 처리하는 메서드입니다.
     * 인증 성공 시 클라이언트가 요청한 리디렉션 URI로 이동합니다.
     *
     * @param code 카카오로부터 받은 인증 코드
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 인증 성공 시 리디렉션 응답, 실패 시 에러 메시지를 담은 ResponseEntity 객체
     */
    @GetMapping("/code")
    public ResponseEntity<?> kakaocode(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
        if (kakaoService.kakaocode(code, request, response, autoLogin)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(requestUrl)); // 성공 시 리디렉션할 URL 설정
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        return ResponseEntity.badRequest().body("failed");
    }
}
