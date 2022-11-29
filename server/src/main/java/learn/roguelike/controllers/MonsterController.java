package learn.roguelike.controllers;

import learn.roguelike.domain.MonsterService;
import learn.roguelike.domain.Result;
import learn.roguelike.domain.ResultType;
import learn.roguelike.models.Monster;
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
@RequestMapping("/api/monster")
public class MonsterController {

    private final MonsterService service;

    public MonsterController(MonsterService service) {
        this.service = service;
    }

    @GetMapping
    public List<Monster> getMonsters() {
        return service.findAll();
    }

    @GetMapping("/{monsterId}")
    public ResponseEntity<Monster> getMonsterById(@PathVariable int monsterId) {
        Monster monster = service.findByMonsterId(monsterId);
        if (monster == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(monster);
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody Monster monster, BindingResult bindingResult,
                                       ServletRequest request){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(makeResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Result<Monster> result = service.add(monster);
        if (result.isSuccess()) {

            String url = String.format("http://%s:%s/api/monster/%s",
                    request.getServerName(),
                    request.getServerPort(),
                    monster.getMonsterId());

            return ResponseEntity.created(URI.create(url))
                    .body(monster);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/edit/{monsterId}")
    public ResponseEntity<Object> put(@PathVariable int monsterId,
                                      @RequestBody @Valid Monster monster,
                                      BindingResult bindingResult) {

        if (monster == null || monster.getMonsterId() != monsterId) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(makeResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Result<Void> result = service.update(monster);
        switch (result.getType()) {
            case SUCCESS:
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            case NOT_FOUND:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{monsterId}")
    public ResponseEntity<Void> delete(@PathVariable int monsterId) {
        boolean success = service.deleteById(monsterId);
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
