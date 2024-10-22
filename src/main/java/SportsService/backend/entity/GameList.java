package SportsService.backend.entity;
import jakarta.persistence.*;
import lombok.*;


@Getter @Setter
@ToString
@EqualsAndHashCode(of= "gameId")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "away_team")
public class GameList {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private String gameId;

    @Column(name = "home_team", nullable = false)
    private String homeTeam;

    @Column(name = "away_team", nullable = false)
    private String awayTeam;

    @Column(name = "home_score", nullable = false)
    private String homeScore;

    @Column(name = "away_score", nullable = false)
    private String awayScore;

    @Column(name = "sports", nullable = false)
    private String sports;

    @Column(name = "league", nullable = false)
    private String league;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "datetime", nullable = false)
    private String datetime;

    @Column(name = "stadium", nullable = false)
    private String stadium;


}
