package learn.roguelike.data;

import learn.roguelike.models.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Integer> {

    Hero findByHeroId(int heroId);
    Hero findByGameId(int gameId);

}

