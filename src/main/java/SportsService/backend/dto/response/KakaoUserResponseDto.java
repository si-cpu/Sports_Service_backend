package SportsService.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 카카오 사용자 정보를 담는 응답 DTO(Data Transfer Object) 클래스입니다.
 * 카카오 OAuth API로부터 받은 사용자 정보를 매핑하여 저장하며,
 * 연결 시점, 프로필 정보 및 계정 정보를 포함합니다.
 *
 * @since 2024-10-23
 * @author minus43
 * @see SportsService.backend.service.KakaoService
 */
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserResponseDto {

    /**
     * 카카오 사용자 고유 ID입니다.
     * 카카오 플랫폼에서 제공하는 사용자 식별용 고유 번호입니다.
     */
    private long id;

    /**
     * 사용자가 카카오에 연결된 시점을 나타냅니다.
     * ISO 8601 형식의 날짜/시간 값으로 제공됩니다.
     */
    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

    /**
     * 사용자 프로필 정보를 담는 내부 클래스입니다.
     * 닉네임, 프로필 이미지 등의 기본적인 프로필 정보를 포함합니다.
     * @see Properties
     */
    private Properties properties;

    /**
     * 사용자 계정 정보를 담는 내부 클래스입니다.
     * 이메일 등의 카카오 계정 정보를 포함합니다.
     * @see KakaoAccount
     */
    @JsonProperty("kakao_account")
    private KakaoAccount account;

    /**
     * 사용자 프로필 정보를 나타내는 내부 클래스입니다.
     * 카카오 플랫폼에서 제공하는 기본적인 사용자 프로필 정보를 포함합니다.
     */
    @Setter @Getter @ToString
    public static class Properties {
        /**
         * 사용자의 닉네임입니다.
         * 카카오 플랫폼에 등록된 사용자의 표시 이름입니다.
         */
        private String nickname;

        /**
         * 사용자의 프로필 이미지 URL입니다.
         * 카카오 플랫폼에서 제공하는 프로필 이미지의 전체 크기 URL입니다.
         */
        @JsonProperty("profile_image")
        private String profileImage;

        /**
         * 사용자의 썸네일 이미지 URL입니다.
         * 카카오 플랫폼에서 제공하는 프로필 이미지의 축소된 크기 URL입니다.
         */
        @JsonProperty("thumbnail_image")
        private String thumbnailImage;
    }

    /**
     * 사용자 계정 정보를 나타내는 내부 클래스입니다.
     * 카카오 계정과 연동된 사용자의 기본 정보를 포함합니다.
     */
    @Setter @Getter @ToString
    public static class KakaoAccount {
        /**
         * 사용자의 이메일 주소입니다.
         * 카카오 계정에 등록된 이메일 주소이며, 사용자의 동의 시에만 제공됩니다.
         */
        private String email;
    }
}
