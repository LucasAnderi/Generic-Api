package org.example.api.service.impl;


import org.example.api.service.GameRestService;
import org.example.db.dao.GameDao;
import org.example.model.entities.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//Deixamos sem UserRestService por enquanto pois não foi necessário
@Service
public class GameRestServiceImpl implements GameRestService<Game> {

    @Autowired
    private GameDao gameDao;


    @Override
    public List<Game> find() {
        return gameDao.find();
    }

    @Override
    public Game findById(int id) {
        if (id < 0) return null;

        return (Game) gameDao.findById(id);
    }

    @Override
    public int create(Game entity) {
        return gameDao.create(entity);
    }

    @Override
    public boolean update(int id, Game entity) {
        Game game = (Game) gameDao.findById(id);

        if(game == null) return false;

        game.setName(entity.getName());
        game.setDescription(entity.getDescription());

        return gameDao.update(game);

    }

    @Override
    public boolean deleteById(int id) {
        return gameDao.deleteById(id);
    }


}
