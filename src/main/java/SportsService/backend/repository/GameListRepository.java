package SportsService.backend.repository;

import SportsService.backend.entity.GameList;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameListRepository
        extends JpaRepository<GameList, String> {


}
