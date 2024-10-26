package SportsService.backend.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * 로그인 상태를 확인하는 유틸리티 클래스입니다.
 * 요청에서 JSESSIONID 쿠키와 세션 정보를 확인하여 로그인 여부를 판단합니다.
 *
 * @since 2024-10-26
 * @author minus43
 */
public class LoginUtils {

    /**
     * 로그인 여부를 확인하는 메서드입니다.
     * 요청에서 JSESSIONID 쿠키와 서버의 세션을 비교하여 로그인 상태를 확인합니다.
     *
     * @param request 로그인 여부를 확인할 HTTP 요청 객체
     * @return 로그인된 사용자의 닉네임 (세션에 저장된 "member" 속성) 또는 로그인되지 않은 경우 null
     */
    public static String isLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JSESSIONID".equals(cookie.getName())) {  // JSESSIONID 쿠키 확인
                    String sessionIdFromCookie = cookie.getValue();  // 쿠키에서 세션 ID 가져오기

                    // request.getSession(false)를 통해 서버가 관리하는 세션을 가져옴
                    HttpSession session = request.getSession(false);

                    // 세션이 존재하고, 쿠키의 세션 ID와 매핑된 세션 ID가 일치하고 "member" 속성이 존재하면
                    if (session != null && session.getId().equals(sessionIdFromCookie)
                            && session.getAttribute("member") != null) {
                        return session.getAttribute("member").toString();  // 로그인된 사용자 정보 반환
                    }
                }
            }
        }
        return null;  // 세션이나 JSESSIONID 쿠키가 없을 때
    }
}
