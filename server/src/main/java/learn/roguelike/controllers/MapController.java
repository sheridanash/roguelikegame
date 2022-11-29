package learn.roguelike.controllers;

import learn.roguelike.domain.MapService;
import learn.roguelike.domain.Result;
import learn.roguelike.domain.ResultType;
import learn.roguelike.models.Map;
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
@RequestMapping("/api/map")
public class MapController {

    private final MapService service;

    public MapController(MapService service) {
        this.service = service;
    }

    @GetMapping
    public List<Map> getMaps() {
        List<Map> maps = service.findAll();
        return maps;
    }

    @GetMapping("/{mapId}")
    public ResponseEntity<Map> getMapById(@PathVariable int mapId) {
        Map map = service.findByMapId(mapId);
        if (map == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody Map map, BindingResult bindingResult,
                                       ServletRequest request) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(makeResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Result<Map> result = service.add(map);
        if (result.isSuccess()) {

            String url = String.format("http://%s:%s/api/map/%s",
                    request.getServerName(),
                    request.getServerPort(),
                    map.getMapId());

            return ResponseEntity.created(URI.create(url))
                    .body(map);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/edit/{mapId}")
    public ResponseEntity<Object> put(@PathVariable int mapId,
                                      @RequestBody @Valid Map map,
                                      BindingResult bindingResult) {

        if (map == null || map.getMapId() != mapId) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(makeResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Result<Void> result = service.update(map);
        switch (result.getType()) {
            case SUCCESS:
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            case NOT_FOUND:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{mapId}")
    public ResponseEntity<Void> delete(@PathVariable int mapId) {
        boolean success = service.deleteById(mapId);
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
