package SportsService.backend.dto.request;

import SportsService.backend.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 회원가입 요청 데이터를 담는 DTO(Data Transfer Object) 클래스입니다.
 * 클라이언트로부터 전달된 회원가입 정보를 서버로 전달하고,
 * User 엔티티로 변환하는 기능을 제공합니다.
 *
 * @since 2024-10-23
 * @author minus43
 * @see SportsService.backend.entity.User
 * @see SportsService.backend.controller.SignController
 */
@Getter
@Setter
@ToString
@Builder
public class SignUpRequestDto {

    /**
     * 사용자의 닉네임입니다.
     * 회원을 식별하는 고유한 값으로 사용되며, null이 허용되지 않습니다.
     */
    private String nickName;

    /**
     * 사용자의 비밀번호입니다.
     * 암호화되어 저장되며, 일반 로그인 시에만 사용됩니다.
     * 소셜 로그인의 경우 null이 허용됩니다.
     */
    private String password;

    /**
     * 사용자의 이메일 주소입니다.
     * 회원 식별 및 연락 수단으로 사용되며, null이 허용되지 않습니다.
     */
    private String email;

    /**
     * 사용자의 로그인 방식입니다.
     * EMAIL, KAKAO, NAVER 중 하나의 값을 가지며,
     * null인 경우 기본값으로 EMAIL이 설정됩니다.
     * @see LoginMethod
     */
    private String loginMethod;

    /**
     * 사용자가 선호하는 MLB 팀입니다.
     */
    private String mlbTeam;

    /**
     * 사용자가 선호하는 KBO 팀입니다.
     */
    private String kboTeam;

    /**
     * 사용자가 선호하는 K-리그 팀입니다.
     */
    private String klTeam;

    /**
     * 사용자가 선호하는 pl-리그 팀입니다.
     */
    private String plTeam;

    /**
     * 사용자가 선호하는 KBL 팀입니다.
     */
    private String kblTeam;

    /**
     * 사용자가 선호하는 NBA 팀입니다.
     */
    private String nbaTeam;

    /**
     * 사용자가 선호하는 V-리그 남자 배구 팀입니다.
     */
    private String vmanTeam;

    /**
     * 사용자가 선호하는 V-리그 여자 배구 팀입니다.
     */
    private String vwoTeam;

    /**
     * 로그인 방법을 나타내는 열거형입니다.
     * EMAIL: 일반 이메일 로그인
     * KAKAO: 카카오 소셜 로그인
     * NAVER: 네이버 소셜 로그인
     */
    public enum LoginMethod {
        EMAIL, KAKAO, NAVER
    }

    /**
     * SignUpRequestDto 객체를 User 엔티티로 변환하는 메서드입니다.
     * 비밀번호는 제공된 PasswordEncoder를 사용하여 암호화됩니다.
     *
     * @param encoder 비밀번호 암호화에 사용할 PasswordEncoder 객체
     * @return User 변환된 User 엔티티 객체
     * @throws IllegalArgumentException 필수 필드가 null인 경우 발생
     */
    public User toUser(PasswordEncoder encoder) {
        return User.builder()
                .nickName(nickName)
                .password(encoder.encode(password))
                .email(email)
                .mlbTeam(mlbTeam)
                .kboTeam(kboTeam)
                .klTeam(klTeam)
                .plTeam(plTeam)
                .kblTeam(kblTeam)
                .nbaTeam(nbaTeam)
                .vmanTeam(vmanTeam)
                .vwoTeam(vwoTeam)
                .loginMethod(makeloginMethod())
                .build();
    }

    /**
     * 로그인 방식을 반환하는 메서드입니다.
     * loginMethod가 null인 경우 기본값으로 EMAIL을 반환합니다.
     *
     * @return String 설정된 로그인 방식 또는 기본값 "EMAIL"
     */
    public String makeloginMethod() {
        if (loginMethod != null) {
            return loginMethod;
        } else {
            return LoginMethod.EMAIL.toString();
        }
    }
}
