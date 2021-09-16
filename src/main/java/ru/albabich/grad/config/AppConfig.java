package ru.albabich.grad.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.albabich.grad.util.JsonUtil;

import java.sql.SQLException;

@Configuration
@Slf4j
@EnableScheduling
@EnableCaching
public class AppConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Profile("!test")
    Server h2Server() throws SQLException {
        log.info("Start H2 TCP server");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    //    https://stackoverflow.com/a/46947975/548473
    @Bean
    Module module() {
        //    https://stackoverflow.com/a/47084295
        return new Hibernate5Module().configure(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
    }

    @Autowired
    public void storeObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.setMapper(objectMapper);
    }

}