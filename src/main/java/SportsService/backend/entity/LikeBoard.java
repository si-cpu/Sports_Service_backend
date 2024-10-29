package SportsService.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "board_num", nullable = false)
    private Board board;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_key", nullable = false)
    private User user;
}
