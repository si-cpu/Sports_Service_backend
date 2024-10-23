package SportsService.backend.dto.request;

import SportsService.backend.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@Getter@Setter@ToString
@Builder
public class SignUpRequestDto {
    private String nickName;
    private String password;
    private String email;
//    private MultipartFile profile;

    private enum LoginMethod {
        EMAIL, KAKAO, NAVER
    }

    public User toUser(PasswordEncoder encoder, String savePath) {
        return User.builder()
                .nickName(nickName)
                .password(encoder.encode(password))
                .email(email)
                .profile(savePath)
                .loginMethod(LoginMethod.EMAIL.toString())
                .auth("COMMON")
                .build();
    }




}
