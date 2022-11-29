package learn.roguelike.data;

import learn.roguelike.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Player findPlayerByUsername(String username);
}
