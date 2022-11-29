package learn.roguelike.domain;

import learn.roguelike.data.MonsterRepository;
import learn.roguelike.models.Monster;
import learn.roguelike.models.Player;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class MonsterService {

    private final MonsterRepository repository;

    public MonsterService(MonsterRepository repository) {
        this.repository = repository;
    }

    public List<Monster> findAll() {
        return repository.findAll();
    }

    public Monster findByMonsterId(int monsterId){
        return repository.findByMonsterId(monsterId);
    }

    public Result<Monster> add(Monster monster){
        Result<Monster> result = validate(monster);
        if(!result.isSuccess()){
            return result;
        }

        monster = repository.save(monster);
        result.setPayload(monster);
        return result;

    }

    public Result<Void> update(Monster monster){
        Result<Void> result = validate(monster);
        if(!result.isSuccess()){
            return result;
        }
        if(findByMonsterId(monster.getMonsterId()) !=null){
            repository.save(monster);
            return result;
        }
        result.addMessage("monster not found", ResultType.INVALID);
        return result;
    }

    public boolean deleteById(int monsterId){
        Monster monster = findByMonsterId(monsterId);
        if(monster !=null){
            repository.deleteById(monsterId);
            return true;
        }
        return false;
    }

    private <T> Result<T> validate(Monster monster){
        Result<T> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Monster>> violations = validator.validate(monster);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Monster> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }
}
