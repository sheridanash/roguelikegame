package learn.roguelike.domain;

import learn.roguelike.data.GameRepository;
import learn.roguelike.data.HeroRepository;
import learn.roguelike.data.MapRepository;
import learn.roguelike.data.TileRepository;
import learn.roguelike.models.Game;
import learn.roguelike.models.Hero;
import learn.roguelike.models.Map;
import learn.roguelike.models.Tile;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class GameService {

    private final GameRepository repository;
    private final MapRepository mapRepository;
    private final TileRepository tileRepository;
    private final HeroRepository heroRepository;
    private final MapService mapService;
    private final TileService tileService;
    private final HeroService heroService;

    public GameService(GameRepository repository, MapRepository mapRepository, TileRepository tileRepository, HeroRepository heroRepository, MapService mapService, TileService tileService, HeroService heroService) {
        this.repository = repository;
        this.mapRepository = mapRepository;
        this.tileRepository = tileRepository;
        this.heroRepository = heroRepository;
        this.mapService = mapService;
        this.tileService = tileService;
        this.heroService = heroService;
    }

    public List<Game> findAll() {
        return repository.findAll();
    }



    public Game findById(int gameId){
        Game game = repository.findByGameId(gameId);
        game.setHero(heroRepository.findByGameId(gameId));
        return game;

    }

    public List<Game> findGamesByPlayerId(int playerId) {
        List<Game> games = repository.findGamesByPlayerId(playerId);
        for (Game g : games) {
            g.setHero(heroRepository.findByGameId(g.getGameId()));
        }
        return games;
    }

    public Result<Game> add(Game game){
        Result<Game> result = validate(game);
        if(!result.isSuccess()){
            return result;
        }
        game = repository.save(game);
        result.setPayload(game);
        return result;

    }
    public Result<Game> createNewGame(Game game){
        Result<Game> result = validate(game);
        Game defaultGame = findById(1);
        if(!result.isSuccess()){
            return null;
        }

        //generating new gameId
        game = repository.save(game);
        result.setPayload(game);
        List<Map> defaultMaps=defaultGame.getMaps();
        int[] mapIds = new int[4];
        //if(result.isSuccess()) {

        //updating defaultMaps with new gameId, generating new mapIds
            for (int i = 0; i < defaultMaps.size(); i++) {
                Map map = new Map();
                map.setGameId(game.getGameId());
                map.setX(defaultMaps.get(i).getX());
                map.setY(defaultMaps.get(i).getY());
                map = mapRepository.save(map);
                mapIds[i] = map.getMapId();

            }
       // }

        //updating Tiles with new mapId
        Tile heroTile = new Tile();
        for(int i = 0; i < defaultMaps.size(); i++){

            for(int k = 0; k < defaultMaps.get(i).getTiles().size(); k++){
                Tile tile = new Tile();
                Tile temp = defaultMaps.get(i).getTiles().get(k);
                tile.setType(temp.getType());
                tile.setMapId(mapIds[i]);
                tile.setX(temp.getX());
                tile.setY(temp.getY());
                tile = tileRepository.save(tile);
                if(i == 0 && k ==0){
                    heroTile = tile;
                }
            }
        }


        //generating new heroId, updating w new gameId, Tile
        Hero hero = new Hero();
        hero.setGameId(game.getGameId());
        hero.setHp(50);
        hero.setLives(3);
        hero.setAir(false);
        hero.setWater(false);
        hero.setEarth(false);
        hero.setFire(false);
        hero.setTile(heroTile);
        hero = heroRepository.save(hero);

        game.setHero(heroRepository.findByHeroId(hero.getHeroId()));
        return result;

    }
    public Result<Void> update(Game game){
        Result<Void> result = validate(game);
        if(!result.isSuccess()){
            return result;
        }
        if(findById(game.getGameId()) !=null){
            repository.save(game);
            return result;
        }
        result.addMessage("game not found", ResultType.INVALID);
        return result;
    }

    public Result<Void> saveGame(Game game){
        Result<Void> result = validate(game);

        Map map = new Map();
        for(int i = 0; i< game.getMaps().size(); i++){
            map = game.getMaps().get(i);
            if(mapService.findByMapId(map.getMapId())!=null){
                mapRepository.save(map);

            }else{
                result.addMessage("map not found", ResultType.INVALID);
            }
        }

        for(int i = 0; i< game.getMaps().size(); i++){
            for(int k = 0; k < game.getMaps().get(i).getTiles().size(); k++){
                Tile temp = game.getMaps().get(i).getTiles().get(k);
                if(tileService.findByTileId(temp.getTileId())!= null){
                    tileRepository.save(temp);
                }else{
                    result.addMessage("tile not found", ResultType.INVALID);
                }
            }
        }
        //game.setHero(heroRepository.findByGameId(game.getGameId()));
        if(heroService.findByHeroId(game.getHero().getHeroId()) != null){
            heroRepository.save(game.getHero());
        }else{
            result.addMessage("hero not found", ResultType.INVALID);
        }

        if(findById(game.getGameId()) !=null){
            repository.save(game);
        }else{
            result.addMessage("game not found", ResultType.INVALID);
        }

        return result;
    }

    public boolean deleteById(int gameId){
        Game game = findById(gameId);
        if(game !=null){
            repository.deleteById(game.getGameId());
            return true;
        }
        return false;
    }

    private <T> Result<T> validate(Game game){
        Result<T> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Game>> violations = validator.validate(game);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Game> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }


}
