package org.example.db.dao.impl;

import org.example.db.connection.ConnectionFactory;
import org.example.db.dao.GameDao;
import org.example.model.entities.Game;
import org.example.model.enums.GameType;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

                game.setName(resultSet.getString("nome"));

                game.setDescription(resultSet.getString("descricao"));

                game.setPrice(resultSet.getDouble("preco"));

                game.setDevelopedBy(resultSet.getString("desenvolvido_por"));

                game.setReleaseDate(resultSet.getString("data_lancamento"));

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
        Game item = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet= null;

        final String sql ="SELECT * FROM jogo WHERE id= ?;";

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                item = new Game();
                item.setId(resultSet.getInt("id"));
                item.setName(resultSet.getString("nome"));
                item.setDescription(resultSet.getString("descricao"));
                item.setPrice(resultSet.getDouble("preco"));
                item.setDevelopedBy(resultSet.getString("desenvolvido_por"));
                item.setReleaseDate(resultSet.getString("data_lancamento"));

                String gameType = resultSet.getString("tipo");
                item.setType(GameType.valueOf(gameType));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConnectionFactory.close(preparedStatement,connection,resultSet);
        }

        return item;
    }

    @Override
    public int create(Game entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int id = -1;

        String sql = "INSERT INTO game(genero, ";
        sql += " nome, descricao, preco, desenvolvido_por, ";
        sql += " data_lancamento, plataforma, imagem_principal, ";
        sql += " criado_em, criado_por, ultima_modificacao ";
        sql += " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try{
            connection = ConnectionFactory.getConnection();

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, GameType.MMORPG.toString());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setDouble(4, entity.getPrice());
            preparedStatement.setString(5, entity.getDevelopedBy());
            preparedStatement.setString(6, entity.getReleaseDate());
            preparedStatement.setString(7, "null");
            preparedStatement.setString(8, null);
            preparedStatement.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(10, "admin");
            preparedStatement.setTimestamp(11, new Timestamp(System.currentTimeMillis()));

            preparedStatement.execute();

            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }

            connection.commit();
            return id;

        }catch (SQLException e) {
            e.printStackTrace();

            try{
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return id;
        }finally {
            ConnectionFactory.close(preparedStatement, connection, resultSet);
        }
    }

    @Override
    public boolean update(Game entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        String sql ="UPDATE jogo SET nome = ?, ";
        sql += "ultima_modificacao = ?, descricao = ?";
        sql += "WHERE";
        sql += "id = ?;";

        try {
            connection = ConnectionFactory.getConnection();

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, entity.getName());

            preparedStatement.setTimestamp(2,new Timestamp(System.currentTimeMillis()));

            preparedStatement.setString(3,entity.getDescription());

            preparedStatement.execute();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }finally{
            ConnectionFactory.close(preparedStatement,connection);
        }
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String sql = "DELETE FROM jogo WHERE id = ?;";

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            connection.setAutoCommit(false);

            preparedStatement.execute();
            connection.commit();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            ConnectionFactory.close(preparedStatement, connection);
        }

        return result;
    }


}
