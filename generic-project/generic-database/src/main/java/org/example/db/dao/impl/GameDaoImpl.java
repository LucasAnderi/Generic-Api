package org.example.db.dao.impl;

import org.example.db.connection.ConnectionFactory;
import org.example.db.dao.GameDao;
import org.example.model.entities.Game;
import org.example.model.enums.GameType;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GameDaoImpl implements GameDao<Game> {

    @Override
    public List<Game> find() {

        List<Game> items = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String sql = "SELECT * FROM jogo ;";

        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Game game = new Game();

                game.setId(resultSet.getInt("id"));
                String gameType = resultSet.getString("genero");
                game.setType(GameType.valueOf(gameType));
                game.setName(resultSet.getString("nome");
                game.setDescription(resultSet.getString("descricao"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement, connection, resultSet);
        }

        return items;


    }

    @Override
    public Game findById(int id) {
        return null;
    }

    @Override
    public int create(Game entity) {
        return 0;
    }

    @Override
    public boolean update(Game entity) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }


}
