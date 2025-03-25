package com.reto_reactivo.bootcamps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.MySqlDialect;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class R2dbcConfig {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Object> converters = new ArrayList<>();
        converters.add(new JsonToSetLongConverter());
        converters.add(new SetLongToJsonConverter());
        return R2dbcCustomConversions.of(MySqlDialect.INSTANCE, converters);
    }
}
