package learn.roguelike.data;

import learn.roguelike.models.Game;
import learn.roguelike.models.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
    Game findByGameId(int gameId);
    List<Game> findGamesByPlayerId(int playerId);
}
