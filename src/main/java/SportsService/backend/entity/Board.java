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
 * 게시판 엔티티로, Sports Service 애플리케이션에서 하나의 게시글을 나타냅니다.
 * 게시글은 제목, 내용, 작성일, 수정일, 조회수, 좋아요 수와 같은 속성을 가집니다.
 *
 * User와 연관되어 있고, 여러 개의 댓글을 가질 수 있습니다.
 * 데이터베이스의 "board" 테이블과 매핑됩니다.
 *
 * @author minus43
 * @since 2024-10-26
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "boardNum")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "board")
public class Board {

    /**
     * 게시글의 고유 식별자입니다.
     * 데이터베이스에서 자동으로 생성됩니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_num")
    private Long boardNum;

    /**
     * 게시글의 제목입니다. null일 수 없습니다.
     */
    @Column(name="title", nullable=false)
    private String title;

    /**
     * 게시글의 내용입니다. null일 수 없습니다.
     */
    @Column(name="content", nullable=false)
    private String content;

    /**
     * 게시글의 작성 날짜와 시간입니다.
     * 게시글 생성 시 자동으로 설정됩니다.
     */
    @Column(name="reg_date")
    @CreationTimestamp
    private LocalDateTime regDate;

    /**
     * 게시글의 마지막 수정 날짜와 시간입니다.
     * 게시글이 수정될 때마다 자동으로 업데이트됩니다.
     */
    @Column(name="mod_date")
    @UpdateTimestamp
    private LocalDateTime modDate;

    /**
     * 게시글의 조회수입니다. 기본값은 0입니다.
     */
    @Column(name="view_count")
    @Builder.Default
    private Long viewCount = 0L;

    /**
     * 게시글의 좋아요(또는 '좋아요') 수입니다. 기본값은 0입니다.
     */
    @Column(name="good_count")
    @Builder.Default
    private Long goodCount = 0L;

    /**
     * 게시글을 작성한 사용자입니다.
     * User 엔티티와 다대일 관계를 가집니다.
     *
     * @see User
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_key", nullable = false)
    private User user;

    /**
     * 이 게시글에 달린 댓글 목록입니다.
     * Reply 엔티티와 일대다 관계를 가지며, 게시글이 삭제될 경우 관련 댓글도 함께 삭제됩니다.
     *
     * @see Reply
     */
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Reply> replies = new ArrayList<>();
}
