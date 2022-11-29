package learn.roguelike.data;

import learn.roguelike.models.Game;
import learn.roguelike.models.Tile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TileRepositoryTest {

    @Autowired
    TileRepository repository;

    @Test
    void shouldFindTiles(){
        var tiles = repository.findAll();
        assertTrue(tiles != null & tiles.size() > 0);
    }

    @Test
    void shouldFindByTileId(){
        Tile tile = repository.findById(1).orElse(null);
        assertNotNull(tile);
    }

    @Test
    void shouldNotFindByTileId(){
        Tile tile = repository.findById(0).orElse(null);
        assertNull(tile);
    }
}
