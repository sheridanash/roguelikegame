package learn.roguelike.data;

import learn.roguelike.models.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonsterRepository extends JpaRepository<Monster, Integer> {

    Monster findByMonsterId(int monsterId);
}
