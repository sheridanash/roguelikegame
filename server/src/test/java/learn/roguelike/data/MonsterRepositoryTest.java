package learn.roguelike.data;

import learn.roguelike.models.Game;
import learn.roguelike.models.Monster;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MonsterRepositoryTest {

    @Autowired
    MonsterRepository repository;

    @Test
    void shouldFindMonsters() {
        var monsters = repository.findAll();
        assertTrue(monsters != null & monsters.size() > 0);
    }
    @Test
    void shouldFindByMonsterId(){
        Monster monster = repository.findById(1).orElse(null);
        assertNotNull(monster);

    }

    @Test
    void shouldNotFindByMonsterId(){
        Monster monster = repository.findById(0).orElse(null);
        assertNull(monster);
    }

}
