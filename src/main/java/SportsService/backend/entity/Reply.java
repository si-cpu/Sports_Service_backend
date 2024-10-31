package SportsService.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 댓글 엔티티로, Sports Service 애플리케이션에서 하나의 댓글을 나타냅니다.
 * 댓글은 내용, 작성일, 수정일, 좋아요 수 등의 속성을 가집니다.
 *
 * User 및 Board와 연관되어 있으며, 데이터베이스의 "reply" 테이블과 매핑됩니다.
 * 각 댓글은 특정 게시글(Board)에 속하며, 특정 사용자(User)가 작성합니다.
 *
 * @author minus43
 * @since 2024-10-26
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "replyNum")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reply")
public class Reply {

    /**
     * 댓글의 고유 식별자입니다.
     * 데이터베이스에서 자동으로 생성됩니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_num")
    private Long replyNum;

    /**
     * 댓글의 내용입니다. null일 수 없습니다.
     */
    @Column(name = "content", nullable = false)
    private String content;

    /**
     * 댓글의 작성 날짜와 시간입니다.
     * 댓글이 생성될 때 자동으로 설정됩니다.
     */
    @Column(name = "reg_date")
    @CreationTimestamp
    private LocalDateTime regDate;

    /**
     * 댓글의 마지막 수정 날짜와 시간입니다.
     * 댓글이 수정될 때마다 자동으로 업데이트됩니다.
     */
    @Column(name = "mod_date")
    @UpdateTimestamp
    private LocalDateTime modDate;

    /**
     * 댓글의 좋아요(또는 '좋아요') 수입니다. 기본값은 0입니다.
     */
    @Column(name = "good_count")
    @Builder.Default
    private Long goodCount = 0L;

    /**
     * 댓글을 작성한 사용자입니다.
     * User 엔티티와 다대일 관계를 가집니다.
     *
     * @see User
     */
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_key", nullable = false)
    private User user;

    /**
     * 이 댓글이 속한 게시글입니다.
     * Board 엔티티와 다대일 관계를 가집니다.
     *
     * @see Board
     */
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "board_num", nullable = false)
    private Board board;

    /**
     * 이 댓글에 대한 좋아요 정보 목록입니다.
     * LikeReply 엔티티와 일대다(1:N) 관계를 가지며, 댓글이 삭제될 경우 관련 좋아요 정보도 함께 삭제됩니다.
     *
     * @see LikeReply
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "reply", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<LikeReply> likes = new ArrayList<>();
}
