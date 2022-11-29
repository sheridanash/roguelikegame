package learn.roguelike.domain;

import learn.roguelike.data.MapRepository;
import learn.roguelike.data.TileRepository;
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
public class MapService {

    private final MapRepository repository;

    public MapService(MapRepository repository){
        this.repository = repository;
    }

    public List<Map> findAll(){
        return repository.findAll();
    }

    public Map findByMapId(int mapId){
        return repository.findByMapId(mapId);
    }

    public Result<Map> add(Map map){
        Result<Map> result = validate(map);
        if(!result.isSuccess()){
            return result;
        }

        map = repository.save(map);
        result.setPayload(map);
        return result;

    }

    public Result<Void> update(Map map){
        Result<Void> result = validate(map);
        if(!result.isSuccess()){
            return result;
        }
        if(findByMapId(map.getMapId()) !=null){
            repository.save(map);
            return result;
        }
        result.addMessage("tile not found", ResultType.INVALID);
        return result;
    }

    public boolean deleteById(int mapId){
        Map map = findByMapId(mapId);
        if(map !=null){
            repository.deleteById(mapId);
            return true;
        }
        return false;
    }

    private <T> Result<T> validate(Map map){
        Result<T> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Map>> violations = validator.validate(map);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Map> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }
}
