package com.reto_reactivo.bootcampeventos.config;

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableR2dbcRepositories
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        // Ajusta según tu configuración de MySQL
        return MySqlConnectionFactory.from(
                MySqlConnectionConfiguration.builder()
                        .host("localhost")
                        .port(3306)
                        .database("bootcamp_eventos_db")
                        .username("root")
                        .password("admin")
                        .build()
        );
    }

    @Override
    protected List<Object> getCustomConverters() {
        List<Object> converters = new ArrayList<>();
        converters.add(new ListLongToJsonConverter());
        converters.add(new JsonToListLongConverter());
        converters.add(new ListStringToJsonConverter());
        converters.add(new JsonToListStringConverter());
        return converters;
    }
}
