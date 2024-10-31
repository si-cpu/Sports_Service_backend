package SportsService.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * 댓글 좋아요 정보를 관리하는 엔티티입니다.
 * 사용자가 댓글에 대해 '좋아요'를 눌렀을 때 생성되는 정보를 저장합니다.
 *
 * <p>주요 특징:</p>
 * <ul>
 *   <li>하나의 댓글과 다대일(N:1) 관계를 가집니다.</li>
 *   <li>하나의 사용자와 다대일(N:1) 관계를 가집니다.</li>
 *   <li>한 사용자는 하나의 댓글에 대해 하나의 좋아요만 할 수 있습니다.</li>
 * </ul>
 *
 * @author minus43
 * @since 2024-10-26
 * @see Reply 좋아요가 달린 댓글
 * @see User 좋아요를 누른 사용자
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "likeReplyNum")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "like_reply")
public class LikeReply {

    /**
     * 댓글 좋아요 정보의 고유 식별자입니다.
     * 자동 생성되는 기본키입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long likeReplyNum;

    /**
     * 좋아요가 달린 댓글 정보입니다.
     * Reply 엔티티와 다대일(N:1) 관계를 가집니다.
     */
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "reply_num", nullable = false)
    private Reply reply;

    /**
     * 좋아요를 누른 사용자 정보입니다.
     * User 엔티티와 다대일(N:1) 관계를 가집니다.
     */
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_key", nullable = false)
    private User user;
}
