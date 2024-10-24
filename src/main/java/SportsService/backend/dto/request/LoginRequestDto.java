package SportsService.backend.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 로그인 요청 데이터를 담는 DTO 클래스입니다.
 * 클라이언트로부터 전달된 로그인 정보를 전달하기 위해 사용됩니다.
 *
 * @author minus43
 * @since 2024-10-23
 */
@Setter
@Getter
@ToString
@Builder
public class LoginRequestDto {

    /**
     * 로그인 시 사용되는 사용자 닉네임입니다.
     */
    private String nickName;

    /**
     * 로그인 시 사용되는 비밀번호입니다.
     */
    private String password;

    /**
     * 자동 로그인 여부를 나타내는 플래그입니다.
     * "true"이면 자동 로그인을 설정합니다.
     */
    private String autoLogin;
}
