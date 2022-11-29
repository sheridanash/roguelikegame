package learn.roguelike.data;

import learn.roguelike.models.Game;
import learn.roguelike.models.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class GameRepositoryTest {

    @Autowired
    GameRepository repository;

    @Test
    void shouldFindGames(){
        var games = repository.findAll();
        assertTrue(games != null & games.size() > 0);
    }

    @Test
    void shouldFindByGameId(){
        Game game = repository.findById(1).orElse(null);
        assertNotNull(game);
    }

    @Test
    void shouldNotFindByGameId(){
        Game game = repository.findById(0).orElse(null);
        assertNull(game);
    }
    @Test
    public void whenParentSavedThenChildSaved() {
//        Game game = new Game();
//        Map map = new Map();
//        List<Map> maps;
//        maps.
//        game.setMaps()
    }


}
