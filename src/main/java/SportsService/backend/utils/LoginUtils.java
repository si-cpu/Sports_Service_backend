package SportsService.backend.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 로그인 관련 유틸리티 기능을 제공하는 클래스입니다.
 * 세션 관리, 쿠키 처리, 로그인 상태 확인 등의 공통 기능을 제공합니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>로그인 상태 확인</li>
 *   <li>세션 관리</li>
 *   <li>쿠키 생성 및 삭제</li>
 *   <li>자동 로그인 처리</li>
 * </ul>
 *
 * @author minus43
 * @since 2024-10-23
 */
public class LoginUtils {

    /**
     * 현재 로그인된 사용자의 닉네임을 반환합니다.
     * 세션에서 로그인 정보를 확인하여 반환합니다.
     *
     * @param request HTTP 요청 객체
     * @return 로그인된 사용자의 닉네임, 비로그인 상태면 null
     */
    public static String isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (String) session.getAttribute("loginMember");
    }

    /**
     * 자동 로그인을 위한 쿠키를 생성합니다.
     *
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param nickName 사용자 닉네임
     * @param autoLogin 자동 로그인 여부
     */
    public static void makeSession(HttpServletRequest request, HttpServletResponse response,
                                 String nickName, String autoLogin) {
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", nickName);

        if (autoLogin != null && autoLogin.equals("true")) {
            Cookie cookie = new Cookie("loginCookie", session.getId());
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 7);  // 7일
            response.addCookie(cookie);
        }
    }

    /**
     * 로그아웃 시 세션과 쿠키를 삭제합니다.
     *
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     */
    public static void expireCookie(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("loginCookie")) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
}
