package SportsService.backend.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 로그인 요청 데이터를 담는 DTO(Data Transfer Object) 클래스입니다.
 * 클라이언트로부터 전달된 로그인 정보를 서버로 전달하기 위해 사용됩니다.
 *
 * @author minus43
 * @since 2024-10-23
 * @see SportsService.backend.service.SignService
 */
@Setter
@Getter
@ToString
@Builder
public class LoginRequestDto {

    /**
     * 로그인 시 사용되는 사용자 닉네임입니다.
     * 일반 로그인 시에는 필수 값이며, 소셜 로그인 시에는 선택적으로 사용됩니다.
     */
    private String nickName;

    /**
     * 로그인 시 사용되는 비밀번호입니다.
     * 일반 로그인 시에만 사용되며, 암호화되어 저장된 비밀번호와 대조됩니다.
     */
    private String password;

    /**
     * 자동 로그인 여부를 나타내는 플래그입니다.
     * "true"이면 자동 로그인을 설정하며, "false"이면 세션 로그인으로 처리됩니다.
     */
    private String autoLogin;

    /**
     * 로그인 방식을 지정하는 열거형 값입니다.
     * NORMAL: 일반 로그인
     * KAKAO: 카카오 소셜 로그인
     * GOOGLE: 구글 소셜 로그인
     * 
     * @see SignUpRequestDto.LoginMethod
     */
    private SignUpRequestDto.LoginMethod loginMethod;

    /**
     * 사용자의 이메일 주소입니다.
     * 소셜 로그인 시 사용자 식별을 위해 사용되며,
     * 일반 로그인 시에는 선택적으로 사용됩니다.
     */
    private String email;
}
