package SportsService.backend.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter@Getter@ToString
@Builder
public class LoginRequestDto {
    private String nickName;
    private String password;
    private Long autoLogin;
}
