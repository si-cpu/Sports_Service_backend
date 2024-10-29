package SportsService.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "likeBoardNum")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "like_board")
public class LikeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long likeBoardNum;


    @ManyToOne
    @JoinColumn(name = "board_num", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_key", nullable = false)
    private User user;
}
