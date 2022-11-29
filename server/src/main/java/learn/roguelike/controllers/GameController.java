package learn.roguelike.controllers;

import learn.roguelike.domain.GameService;
import learn.roguelike.domain.Result;
import learn.roguelike.domain.ResultType;
import learn.roguelike.models.Game;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
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
@RequestMapping("/api/game")
//@ConditionalOnWebApplication
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    public List<Game> getGames(){
        List<Game> games = service.findAll();
        return games;
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGameById(@PathVariable int gameId) {
        Game game = service.findById(gameId);
        if (game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
    }

    @GetMapping("/player/{playerId}")
    public List<Game> getGamesByPlayerId(@PathVariable int playerId) {
        List<Game> games = service.findGamesByPlayerId(playerId);
        return games;
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody Game game, BindingResult bindingResult,
                                       ServletRequest request){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(makeResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Result<Game> result = service.add(game);
        if (result.isSuccess()) {

            String url = String.format("http://%s:%s/api/game/%s",
                    request.getServerName(),
                    request.getServerPort(),
                    game.getGameId());

            return ResponseEntity.created(URI.create(url))
                    .body(game);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/create")
    public ResponseEntity<Object> postNew (@RequestBody Game game, BindingResult bindingResult,
                                       ServletRequest request){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(makeResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Result<Game> result = service.createNewGame(game);
        if (result.isSuccess()) {

            String url = String.format("http://%s:%s/api/game/%s",
                    request.getServerName(),
                    request.getServerPort(),
                    game.getGameId());

            return ResponseEntity.created(URI.create(url))
                    .body(game);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/edit/{gameId}")
    public ResponseEntity<Object> put(@PathVariable int gameId,
                                      @RequestBody @Valid Game game,
                                      BindingResult bindingResult) {

        if (game == null || game.getGameId() != gameId) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(makeResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Result<Void> result = service.update(game);
        switch (result.getType()) {
            case SUCCESS:
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            case NOT_FOUND:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/save/{gameId}")
    public ResponseEntity<Object> putGame(@PathVariable int gameId,
                                      @RequestBody @Valid Game game,
                                      BindingResult bindingResult) {

        if (game == null || game.getGameId() != gameId) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(makeResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Result<Void> result = service.saveGame(game);
        switch (result.getType()) {
            case SUCCESS:
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            case NOT_FOUND:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> delete(@PathVariable int gameId) {
        boolean success = service.deleteById(gameId);
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
