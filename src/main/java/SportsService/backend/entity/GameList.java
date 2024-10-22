package SportsService.backend.entity;

/*
    game_id VARCHAR(1000) NOT NULL UNIQUE
    home_team VARCHAR(1000) NOT NULL
    away_team VARCHAR(1000) NOT NULL
    home_score VARCHAR(1000) NOT NULL
    away_score VARCHAR(1000) NOT NULL
    home_team_logo VARCHAR(1000) NOT NULL
    away_team_logo VARCHAR(1000) NOT NULL
    sports VARCHAR(1000) NOT NULL
    league VARCHAR(1000) NOT NULL
    status VARCHAR(1000) NOT NULL
    datetime VARCHAR(1000) NOT NULL
    stadium VARCHAR(1000) NOT NULL
 */


//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
import jakarta.annotation.Nullable;
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
