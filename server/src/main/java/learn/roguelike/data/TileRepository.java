package learn.roguelike.data;


import learn.roguelike.models.Tile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TileRepository extends JpaRepository<Tile, Integer> {

    Tile findByTileId(int tileId);
}
