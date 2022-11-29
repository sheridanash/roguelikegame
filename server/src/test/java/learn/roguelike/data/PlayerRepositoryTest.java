package learn.roguelike.data;


import learn.roguelike.models.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PlayerRepositoryTest {

    @Autowired
    PlayerRepository repository;

    @Test
    void shouldPlayers() {
        var players = repository.findAll();
        assertTrue( players!= null & players.size() > 0);
    }

    @Test
    void shouldFindByPlayerId(){
        Player player = repository.findById(1).orElse(null);
        assertNotNull(player);

    }

    @Test
    void shouldFindPlayerByUsername(){
        Player pagoto = repository.findPlayerByUsername("pagoto");
        assertEquals("pagoto", pagoto.getUsername());
    }

}
