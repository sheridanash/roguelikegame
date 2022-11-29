package learn.roguelike.controllers;

import learn.roguelike.domain.Result;
import learn.roguelike.domain.ResultType;
import learn.roguelike.domain.TileService;
import learn.roguelike.models.Tile;
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
@RequestMapping("/api/tile")
public class TileController {

    private final TileService service;

    public TileController(TileService service){
        this.service = service;
    }

    @GetMapping
    public List<Tile> getTiles(){
        List<Tile> tiles = service.findAll();
        return tiles;
    }

    @GetMapping("/{tileId}")
    public ResponseEntity<Tile> getTileById(@PathVariable int tileId) {
        Tile tile = service.findByTileId(tileId);
        if (tile == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tile);
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody Tile tile, BindingResult bindingResult,
                                       ServletRequest request){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(makeResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Result<Tile> result = service.add(tile);
        if (result.isSuccess()) {

            String url = String.format("http://%s:%s/api/tile/%s",
                    request.getServerName(),
                    request.getServerPort(),
                    tile.getTileId());

            return ResponseEntity.created(URI.create(url))
                    .body(tile);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/edit/{tileId}")
    public ResponseEntity<Object> put(@PathVariable int tileId,
                                      @RequestBody @Valid Tile tile,
                                      BindingResult bindingResult) {

        if (tile == null || tile.getTileId() != tileId) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(makeResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Result<Void> result = service.update(tile);
        switch (result.getType()) {
            case SUCCESS:
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            case NOT_FOUND:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{tileId}")
    public ResponseEntity<Void> delete(@PathVariable int tileId) {
        boolean success = service.deleteById(tileId);
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
