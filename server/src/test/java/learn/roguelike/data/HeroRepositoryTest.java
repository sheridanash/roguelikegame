package learn.roguelike.data;

import learn.roguelike.models.Game;
import learn.roguelike.models.Hero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class HeroRepositoryTest {

    @Autowired
    HeroRepository repository;

    @Test

    void shouldFindHeroes() {
        var heroes = repository.findAll();
        assertTrue(heroes != null & heroes.size() > 0);
    }

    @Test
    void shouldFindByHeroId(){
        Hero hero = repository.findById(1).orElse(null);
        assertNotNull(hero);

    }

    @Test
    void shouldNotFindByHeroId(){
        Hero hero = repository.findById(0).orElse(null);
        assertNull(hero);
    }


}
