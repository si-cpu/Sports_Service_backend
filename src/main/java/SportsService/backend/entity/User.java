package SportsService.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

/**
 * 이 클래스는 시스템 내에서 사용자를 나타내는 엔티티입니다.
 * 닉네임, 이메일, 비밀번호와 같은 다양한 사용자 정보를 포함하며,
 * 사용자의 선호 스포츠 팀에 대한 정보도 담고 있습니다.
 * 이 엔티티는 데이터베이스의 "user" 테이블과 매핑됩니다.
 *
 * @author minus43
 * @since 2024-10-23
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "userKey")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    /**
     * 사용자에 대한 고유 식별자입니다.
     * 데이터베이스에서 자동으로 생성됩니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_key")
    private Long userKey;

    /**
     * 사용자의 닉네임입니다.
     * 이 값은 필수이며 null일 수 없습니다.
     */
    @Column(name = "nick_name", nullable = false)
    private String nickName;

    /**
     * 사용자의 비밀번호입니다.
     * 이 값은 필수이며 null일 수 없습니다.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 사용자의 이메일 주소입니다.
     * 이 값은 필수이며 null일 수 없습니다.
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * 사용자가 등록된 날짜와 시간입니다.
     * 이 값은 사용자가 생성될 때 자동으로 설정됩니다.
     */
    @Column(name = "reg_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

    /**
     * 사용자의 권한 레벨 또는 역할(예: ADMIN, USER)입니다.
     * 이 값은 필수이며 null일 수 없습니다.
     */
    @Column(name = "auth", nullable = false)
    private String auth;

    /**
     * 사용자가 로그인할 때 사용한 방법(예: EMAIL, KAKAO, NAVER)입니다.
     * 이 값은 필수이며 null일 수 없습니다.
     */
    @Column(name = "login_method", nullable = false)
    private String loginMethod;

    @Column(name="profile")
    private String profile;


    /**
     * 사용자가 선호하는 MLB 팀입니다.
     */
    @Column(name = "mlb_team")
    private String mlbTeam;

    /**
     * 사용자가 선호하는 KBO 팀입니다.
     */
    @Column(name = "kbo_team")
    private String kboTeam;

    /**
     * 사용자가 선호하는 K-리그 팀입니다.
     */
    @Column(name = "kl_team")
    private String klTeam;

    /**
     * 사용자가 선호하는 프리미어리그 팀입니다.
     */
    @Column(name = "pl_team")
    private String plTeam;

    /**
     * 사용자가 선호하는 KBL (한국 농구 리그) 팀입니다.
     */
    @Column(name = "kbl_team")
    private String kblTeam;

    /**
     * 사용자가 선호하는 NBA 팀입니다.
     */
    @Column(name = "nba_team")
    private String nbaTeam;

    /**
     * 사용자가 선호하는 V-리그 남자 배구 팀입니다.
     */
    @Column(name = "vman_team")
    private String vmanTeam;

    /**
     * 사용자가 선호하는 V-리그 여자 배구 팀입니다.
     */
    @Column(name = "vwo_team")
    private String vwoTeam;

}
