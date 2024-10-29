package SportsService.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long likeReplyNum;


    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "reply_num", nullable = false)
    private Reply reply;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_key", nullable = false)
    private User user;
}
