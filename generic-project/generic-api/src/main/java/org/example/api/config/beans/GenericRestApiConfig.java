package org.example.api.config.beans;

import org.example.db.dao.GameDao;
import org.example.db.dao.impl.GameDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenericRestApiConfig {

    @Bean
    public GameDao getGameDao() {
        return new GameDaoImpl();
    }

}
