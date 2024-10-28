package SportsService.backend.dto.request;

import SportsService.backend.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 회원가입 요청 데이터를 담는 DTO 클래스입니다.
 * 회원의 닉네임, 비밀번호, 이메일 및 선호하는 스포츠 팀에 대한 정보를 포함합니다.
 * 이 데이터를 바탕으로 User 엔티티를 생성할 수 있습니다.
 *
 * @author minus43
 * @since 2024-10-23
 */
@Getter
@Setter
@ToString
@Builder
public class SignUpRequestDto {

    /**
     * 사용자의 닉네임입니다.
     * 이 값은 필수이며 null일 수 없습니다.
     */
    private String nickName;

    /**
     * 사용자의 비밀번호입니다.
     * 이 값은 필수이며 null일 수 없습니다.
     */
    private String password;

    /**
     * 사용자의 이메일입니다.
     * 이 값은 필수이며 null일 수 없습니다.
     */
    private String email;

    /**
     * 사용자의 로그인 방식입니다.
     * 이 값은 필수이며 null일 수 없습니다.
     * LoginMethod의 값 중에서 하나를 선택합니다.
     * 로그인하는 방식에 따라 정해집니다.
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
     * EMAIL, KAKAO, NAVER 방식으로 구분됩니다.
     */
    public enum LoginMethod {
        EMAIL, KAKAO, NAVER
    }

    /**
     * SignUpRequestDto 객체를 User 엔티티로 변환하는 메서드입니다.
     * 비밀번호는 암호화된 상태로 저장됩니다.
     *
     * @param encoder 비밀번호를 암호화하는 PasswordEncoder 객체
     * @return 변환된 User 엔티티
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
     * 로그인 방식을 반환합니다. loginMethod가 null이면 기본적으로 EMAIL 방식을 반환합니다.
     *
     * @return 지정된 로그인 방식이 있으면 그 값을, 없으면 기본값 EMAIL을 반환
     */
    public String makeloginMethod() {
        if (loginMethod != null) {
            return loginMethod;
        } else {
            return LoginMethod.EMAIL.toString();
        }
    }
}
