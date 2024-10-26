package SportsService.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 카카오 사용자 정보를 담는 응답 DTO 클래스입니다.
 * 카카오에서 전달된 사용자 정보를 저장하며, 연결 시점, 프로필 정보 및 계정 정보를 포함합니다.
 *
 * @since 2024-10-23
 * @author minus43
 */
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserResponseDto {

    /**
     * 카카오 사용자 고유 ID입니다.
     */
    private long id;

    /**
     * 사용자가 카카오에 연결된 시점을 나타냅니다.
     */
    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

    /**
     * 사용자 프로필 정보를 담는 내부 클래스입니다.
     */
    private Properties properties;

    /**
     * 사용자 계정 정보를 담는 내부 클래스입니다.
     */
    @JsonProperty("kakao_account")
    private KakaoAccount account;

    /**
     * 사용자 프로필 정보를 나타내는 클래스입니다.
     * 닉네임, 프로필 이미지 및 썸네일 이미지를 포함합니다.
     *
     * @since 2024-10-23
     * @author minus43
     */
    @Setter @Getter @ToString
    public static class Properties {
        /**
         * 사용자의 닉네임입니다.
         */
        private String nickname;

        /**
         * 사용자의 프로필 이미지 URL입니다.
         */
        @JsonProperty("profile_image")
        private String profileImage;

        /**
         * 사용자의 썸네일 이미지 URL입니다.
         */
        @JsonProperty("thumbnail_image")
        private String thumbnailImage;
    }

    /**
     * 사용자 계정 정보를 나타내는 클래스입니다.
     * 이메일 정보를 포함합니다.
     *
     * @since 2024-10-23
     * @author minus43
     */
    @Setter @Getter @ToString
    public static class KakaoAccount {
        /**
         * 사용자의 이메일 주소입니다.
         */
        private String email;
    }
}
