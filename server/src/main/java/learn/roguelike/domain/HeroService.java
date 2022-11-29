package learn.roguelike.domain;

import learn.roguelike.data.HeroRepository;
import learn.roguelike.models.Hero;
import learn.roguelike.models.Player;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HeroService {

    private final HeroRepository repository;

    public HeroService(HeroRepository repository){
        this.repository = repository;
    }

    public List<Hero> findAll(){
        return repository.findAll();
    }

    public Hero findByHeroId(int heroId){
        return repository.findByHeroId(heroId);
    }

    public Result<Hero> add(Hero hero){
        Result<Hero> result = validate(hero);
        if(!result.isSuccess()){
            return result;
        }

        hero = repository.save(hero);
        result.setPayload(hero);
        return result;

    }

    public Result<Void> update(Hero hero){
        Result<Void> result = validate(hero);
        if(!result.isSuccess()){
            return result;
        }
        if(findByHeroId(hero.getHeroId()) !=null){
            repository.save(hero);
            return result;
        }
        result.addMessage("hero not found", ResultType.INVALID);
        return result;
    }

    public boolean deleteById(int heroId){
        Hero hero = findByHeroId(heroId);
        if(hero !=null){
            repository.deleteById(hero.getHeroId());
            return true;
        }
        return false;
    }

    private <T> Result<T> validate(Hero hero){
        Result<T> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Hero>> violations = validator.validate(hero);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Hero> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }
}
