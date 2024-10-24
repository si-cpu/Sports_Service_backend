package SportsService.backend.entity;

import SportsService.backend.dto.request.CommunityBoardWriteRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@EqualsAndHashCode(of = "boardNum")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "com_board")
public class CommunityBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_num")
    private long boardNum;

    @Column(name = "user_key", nullable = false)
    private long userKey;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "reg_time", nullable = false)
    @CreationTimestamp
    private LocalDateTime regTime;

    @Column(name = "modify_time")
    private LocalDateTime modifyTime;

    @Column(name = "view_count", nullable = false)
    private long viewCount;

    @Column(name = "good_board", nullable = false)
    private long goodBoard;

    ///////////////writer 임시항목////////////////////////
    @Column(name = "writer", nullable = false)
    private String writer;

    public CommunityBoard(CommunityBoardWriteRequestDTO dto) {
        this.writer = dto.getWriter();
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void newWrite(CommunityBoardWriteRequestDTO dto) {
        this.writer = dto.getWriter();
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }
}

/*
CREATE TABLE com_board (
	board_num INT PRIMARY KEY AUTO_INCREMENT,
    user_key INT, 외래키

    title VARCHAR(150) NOT NULL,
    content VARCHAR(1000) NOT NULL,
    reg_time DATETIME DEFAULT current_timestamp NOT NULL,
    modify_time DATETIME DEFAULT current_timestamp,
    view_count INT NOT NULL,
    good_board INT NOT NULL
); */