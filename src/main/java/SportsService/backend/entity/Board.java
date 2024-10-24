package SportsService.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_num")
    private Long boardNum;

    @Column(name="title", nullable=false)
    private String title;

    @Column(name="content", nullable=false)
    private String content;

    @Column(name="reg_date")
    @CreationTimestamp
    private LocalDateTime regDate;

    @Column(name="mod_date")
    private LocalDateTime modDate;

    @Column(name="view_count")
    @Builder.Default
    private Long viewCount=0L;

    @Column(name="good_count")
    @Builder.Default
    private Long goodCount=0L;

    @ManyToOne
    @JoinColumn(name = "user_key", nullable = false)
    private User user;


}
