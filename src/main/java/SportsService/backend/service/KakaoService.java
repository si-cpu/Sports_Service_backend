package SportsService.backend.service;

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
 * 카카오 소셜 로그인 기능을 처리하는 서비스 클래스입니다.
 * 카카오 OAuth2.0을 통한 인증 및 사용자 정보 조회, 회원가입, 로그인 기능을 제공합니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>카카오 로그인 페이지 URL 생성</li>
 *   <li>인증 코드를 통한 액세스 토큰 발급</li>
 *   <li>액세스 토큰을 통한 사용자 정보 조회</li>
 *   <li>카카오 계정을 통한 회원가입 및 로그인</li>
 * </ul>
 *
 * @author minus43
 * @since 2024-10-23
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
     * 카카오 인증 페이지의 URL을 생성하여 반환합니다.
     * OAuth2.0 인증을 위한 필수 파라미터들을 포함합니다.
     *
     * @return 카카오 인증 페이지 URL이 포함된 Map 객체
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
     * 신규 사용자의 경우 자동으로 회원가입을 진행하고, 기존 사용자는 로그인 처리합니다.
     *
     * @param code 카카오 인증 서버에서 받은 인증 코드
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param autoLogin 자동 로그인 여부
     * @return 로그인 처리 성공 여부
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
     * 카카오 인증 코드를 사용하여 액세스 토큰을 발급받습니다.
     * 카카오 토큰 발급 API를 호출하여 액세스 토큰을 획득합니다.
     *
     * @param code 카카오 인증 서버에서 받은 인증 코드
     * @return 발급받은 액세스 토큰
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
     * 액세스 토큰을 사용하여 카카오 사용자 정보를 조회합니다.
     * 카카오 사용자 정보 API를 호출하여 이메일, 닉네임 등의 정보를 획득합니다.
     *
     * @param accessToken 카카오 API 접근을 위한 액세스 토큰
     * @return 카카오 사용자 정보가 담긴 DTO 객체
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
