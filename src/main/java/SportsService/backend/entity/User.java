package SportsService.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 이 클래스는 시스템 내에서 사용자를 나타내는 엔티티입니다.
 * 닉네임, 이메일, 비밀번호와 같은 다양한 사용자 정보를 포함하며,
 * 사용자의 선호 스포츠 팀에 대한 정보도 담고 있습니다.
 * 이 엔티티는 데이터베이스의 "user" 테이블과 매핑됩니다.
 *
 * 사용자와 게시판 게시물 및 댓글의 연관 관계를 관리합니다.
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
    @Builder.Default
    private String auth="COMMON";

    /**
     * 사용자가 로그인할 때 사용한 방법(예: EMAIL, KAKAO, NAVER)입니다.
     * 이 값은 필수이며 null일 수 없습니다.
     */
    @Column(name = "login_method", nullable = false)
    private String loginMethod;

    /**
     * 사용자의 프로필 정보입니다.
     */
    @Column(name = "profile")
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

    /**
     * 사용자가 작성한 게시글 목록입니다.
     * 게시글이 삭제될 경우 해당 게시글들도 함께 삭제됩니다.
     *
     * @see Board
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();

    /**
     * 사용자가 작성한 댓글 목록입니다.
     * 댓글이 삭제될 경우 해당 댓글들도 함께 삭제됩니다.
     *
     * @see Reply
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Reply> replies = new ArrayList<>();

    /**
     * 사용자가 좋아요를 누른 게시글 정보 목록입니다.
     * LikeBoard 엔티티와 일대다(1:N) 관계를 가지며, 사용자가 삭제될 경우 관련 좋아요 정보도 함께 삭제됩니다.
     *
     * @see LikeBoard
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<LikeBoard> likeBoardList = new ArrayList<>();

    /**
     * 사용자가 좋아요를 누른 댓글 정보 목록입니다.
     * LikeReply 엔티티와 일대다(1:N) 관계를 가지며, 사용자가 삭제될 경우 관련 좋아요 정보도 함께 삭제됩니다.
     *
     * @see LikeReply
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<LikeReply> likeReplyList = new ArrayList<>();
}
