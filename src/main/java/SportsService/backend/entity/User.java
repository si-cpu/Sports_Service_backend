package SportsService.backend.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of= "userKey")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_key")
    private Long userKey;

    @Column(name = "nick_name", nullable = false, unique = true)
    private String nickName;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="reg_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

    @Column(name="auth", nullable = false)
    private String auth;

    @Column(name="mlb_team")
    private String mlbTeam;

    @Column(name="kbo_team")
    private String kboTeam;

    @Column(name="kl_team")
    private String klTeam;

    @Column(name="pl_team")
    private String plTeam;

    @Column(name="kbl_team")
    private String kblTeam;

    @Column(name="nba_team")
    private String nbaTeam;

    @Column(name="vman_team")
    private String vmanTeam;

    @Column(name="vwo_team")
    private String vwoTeam;

}
