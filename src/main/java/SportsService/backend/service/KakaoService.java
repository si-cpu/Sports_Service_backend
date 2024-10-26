package SportsService.backend.service;

import SportsService.backend.controller.MemberController;
import SportsService.backend.dto.request.LoginRequestDto;
import SportsService.backend.dto.request.SignUpRequestDto;
import SportsService.backend.dto.response.KakaoUserResponseDto;
import SportsService.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * 카카오 인증 및 사용자 정보 관리를 위한 서비스 클래스입니다.
 * 사용자 정보를 카카오 API와 통신하여 가져오고, 이를 회원가입 및 로그인에 활용합니다.
 *
 * @since 2024-10-23
 * @author minus43
 */
@Service
@Transactional
@RequiredArgsConstructor
public class KakaoService {

    /**
     * 사용자 정보를 저장하는 UserRepository 객체입니다.
     */
    private final UserRepository userRepository;

    /**
     * 회원 가입 및 기타 회원 관련 작업을 처리하는 MemberService 객체입니다.
     */
    private final MemberService memberService;

    /**
     * 카카오 앱 키입니다.
     */
    @Value("${sns.kakao.app-key}")
    private String appKey;

    /**
     * 카카오 리다이렉트 URI입니다.
     */
    @Value("${sns.kakao.redirect-uri}")
    private String redirectUri;

    /**
     * 카카오 인증 서버에서 액세스 토큰을 얻기 위한 URL입니다.
     */
    @Value("${sns.kakao.token-uri}")
    private  String TOKEN_URL;

    /**
     * 카카오 인증 페이지의 URL을 반환합니다.
     *
     * @return 카카오 인증 페이지로 리디렉션하는 URL 정보를 담은 Map
     */
    public Map<String, String> getKakaoAuthPageUrlHeaders() {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + appKey
                + "&redirect_uri=" + redirectUri
                + "&response_type=code";

        Map<String, String> response = new HashMap<>();
        response.put("redirectUri", kakaoAuthUrl);
        return response;
    }

    /**
     * 카카오 인증 코드를 사용하여 회원가입 및 로그인을 처리합니다.
     *
     * @param code 카카오로부터 받은 인증 코드
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param autoLogin 자동 로그인 여부
     * @return 회원가입 및 로그인 성공 여부
     */
    public boolean kakaocode(String code, HttpServletRequest request, HttpServletResponse response, String autoLogin) {
        String accessToken = getAccessToken(code);
        KakaoUserResponseDto kakaoUser = getUserInfo(accessToken);

        if (!userRepository.findByEmail(kakaoUser.getAccount().getEmail()).isPresent()) {
            memberService.signUp(SignUpRequestDto.builder()
                    .nickName(kakaoUser.getProperties().getNickname())
                    .password(UUID.randomUUID().toString())
                    .email(kakaoUser.getAccount().getEmail())
                    .loginMethod(SignUpRequestDto.LoginMethod.KAKAO.toString())
                    .build()
            );
        }
        LoginRequestDto kakaoUserLoginDto = LoginRequestDto.builder()
                .nickName(kakaoUser.getProperties().getNickname())
                .autoLogin(autoLogin)
                .build();
        memberService.makeCookie(kakaoUserLoginDto, request, response);
        return true;
    }

    /**
     * 인증 코드로부터 카카오 액세스 토큰을 얻습니다.
     *
     * @param code 카카오로부터 받은 인증 코드
     * @return 액세스 토큰 문자열
     */
    public String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", appKey);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, requestEntity, Map.class);

        Map<String, Object> responseBody = response.getBody();
        return (String) Objects.requireNonNull(responseBody).get("access_token");
    }

    /**
     * 카카오 액세스 토큰을 사용하여 사용자 정보를 가져옵니다.
     *
     * @param accessToken 카카오로부터 발급받은 액세스 토큰
     * @return 카카오 사용자 정보를 담은 KakaoUserResponseDto 객체
     */
    public KakaoUserResponseDto getUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<KakaoUserResponseDto> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, KakaoUserResponseDto.class);
        KakaoUserResponseDto userInfo = response.getBody();

        return userInfo;
    }
}
