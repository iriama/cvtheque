package archapp.web;

import archapp.SpringConfiguration;
import archapp.dto.PersonDto;
import archapp.model.User;
import archapp.repository.UserRepository;
import archapp.security.WebSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active:test")
class PersonControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebTestClient client;

    private User test1;

    @BeforeAll
    public void setup() {
        test1 = new User(
                "test1@mail.com",
                "test1234",
                "test1firstname",
                "test1lastname",
                "http://test1.com"
        );

        userRepository.save(test1);
    }

    @AfterAll
    public void teardown() {
        userRepository.deleteAll();
    }


    @Test
    void persons() {
        client.get().uri("/api/persons").exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().jsonPath("$[0].firstname").isEqualTo(test1.getFirstname());
    }

    @Test
    void getUserById() {
    }
}