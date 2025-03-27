package com.reto_reactivo.tecnologia;

import com.reto_reactivo.tecnologia.domain.port.out.TecnologiaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.TestPropertySource;
import reactor.test.StepVerifier;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class TecnologiaApplicationTests {

    @Test
    void contextLoads() {
    }
}