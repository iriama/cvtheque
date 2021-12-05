package archapp.web;


import archapp.model.User;
import archapp.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active:test")
class PersonControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebTestClient client;

    private User test1;

    @BeforeAll
    public void setup() {
        test1 = new User(
                "test1@mail.com",
                passwordEncoder.encode("test1234"),
                "test1firstname",
                "test1lastname",
                "http://test1.com"
        );

        test1 = userRepository.save(test1);
    }

    @AfterAll
    public void teardown() {
        userRepository.deleteAll();
    }


    @Test
    void persons() {
        client.get().uri("/api/persons").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$[0].firstname").isEqualTo(test1.getFirstname());
    }

    @Test
    void getUserById() {
        client.get().uri("/api/persons/" + test1.getId()).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.firstname").isEqualTo(test1.getFirstname());
    }
}