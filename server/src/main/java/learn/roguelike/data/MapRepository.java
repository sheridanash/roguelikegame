package learn.roguelike.data;

import learn.roguelike.models.Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map, Integer> {

    Map findByMapId(int mapId);
}
