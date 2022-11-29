package learn.roguelike.controllers;

import learn.roguelike.domain.HeroService;
import learn.roguelike.domain.Result;
import learn.roguelike.domain.ResultType;
import learn.roguelike.models.Hero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hero")
public class HeroController {

    private final HeroService service;

    public HeroController(HeroService service){
        this.service = service;
    }

    @GetMapping
    public List<Hero> getHeroes() {
        return service.findAll();
    }

    @GetMapping("/{heroId}")
    public Hero getHeroById(@PathVariable int heroId){
        return service.findByHeroId(heroId);
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody Hero hero, BindingResult bindingResult,
                                       ServletRequest request){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(makeResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Result<Hero> result = service.add(hero);
        if (result.isSuccess()) {

            String url = String.format("http://%s:%s/api/hero/%s",
                    request.getServerName(),
                    request.getServerPort(),
                    hero.getHeroId());

            return ResponseEntity.created(URI.create(url))
                    .body(hero);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/edit/{heroId}")
    public ResponseEntity<Object> put(@PathVariable int heroId,
                                      @RequestBody @Valid Hero hero,
                                      BindingResult bindingResult) {

        if (hero == null || hero.getHeroId() != heroId) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(makeResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Result<Void> result = service.update(hero);
        switch (result.getType()) {
            case SUCCESS:
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            case NOT_FOUND:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{heroId}")
    public ResponseEntity<Void> delete(@PathVariable int heroId) {
        boolean success = service.deleteById(heroId);
        if (success) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Result<Void> makeResult(BindingResult bindingResult) {
        Result<Void> result = new Result<>();
        for (ObjectError err : bindingResult.getAllErrors()) {
            result.addMessage(err.getDefaultMessage(), ResultType.INVALID);
        }
        return result;
    }
}

