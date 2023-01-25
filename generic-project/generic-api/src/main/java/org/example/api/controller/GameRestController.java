package org.example.api.controller;


import org.example.api.service.GameRestService;
import org.example.model.entities.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins="*")
public class GameRestController {

    @Autowired
    private GameRestService<Game> gameService;

    @GetMapping("/find")
    public ResponseEntity<List<Game>> find(){

        List<Game> games = gameService.find();

        if(games == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(games);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Game> findById(@PathVariable("id") final int id){
        Game game = gameService.findById(id);

        if(game==null){

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") final int id){
        boolean result = gameService.deleteById(id);

        return result ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @PostMapping("/create")
    public ResponseEntity<Integer> create(@RequestBody final Game game){
        int gameId = gameService.create(game);

        return gameId == -1 ? ResponseEntity.badRequest().build():ResponseEntity.ok(gameId);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") final int id,@RequestBody final Game entity){
        boolean response = gameService.update(id,entity);

        return response ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
    }


}
