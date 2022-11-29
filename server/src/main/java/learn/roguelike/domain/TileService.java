package learn.roguelike.domain;


import learn.roguelike.data.TileRepository;
import learn.roguelike.models.Monster;
import learn.roguelike.models.Tile;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class TileService {

    private final TileRepository repository;

    public TileService(TileRepository repository){
        this.repository = repository;
    }

    public List<Tile> findAll(){
        return repository.findAll();
    }

    public Tile findByTileId(int tileId){
        return repository.findByTileId(tileId);
    }

    public Result<Tile> add(Tile tile){
        Result<Tile> result = validate(tile);
        if(!result.isSuccess()){
            return result;
        }

        tile = repository.save(tile);
        result.setPayload(tile);
        return result;

    }

    public Result<Void> update(Tile tile){
        Result<Void> result = validate(tile);
        if(!result.isSuccess()){
            return result;
        }
        if(findByTileId(tile.getTileId()) !=null){
            repository.save(tile);
            return result;
        }
        result.addMessage("tile not found", ResultType.INVALID);
        return result;
    }

    public boolean deleteById(int tileId){
        Tile tile = findByTileId(tileId);
        if(tile !=null){
            repository.deleteById(tileId);
            return true;
        }
        return false;
    }

    private <T> Result<T> validate(Tile tile){
        Result<T> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Tile>> violations = validator.validate(tile);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Tile> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }

}
