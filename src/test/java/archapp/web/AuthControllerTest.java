package archapp.web;

import archapp.dto.UserAuthDto;
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
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active:test")
class AuthControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User test1;
    private UserAuthDto authValid;
    private UserAuthDto authInvalid;

    @BeforeAll
    public void setup() {
        test1 = new User(
                "test1@mail.com",
                passwordEncoder.encode("test1234"),
                "test1firstname",
                "test1lastname",
                "http://test1.com"
        );

        authInvalid = new UserAuthDto();
        authInvalid.setEmail("test1@mail.com");
        authInvalid.setPassword("test12345");

        authValid = new UserAuthDto();
        authValid.setEmail("test1@mail.com");
        authValid.setPassword("test1234");

        userRepository.save(test1);
    }

    @AfterAll
    public void teardown() {
        userRepository.deleteAll();
    }

    @Test
    void signinValid() {
        client.post().uri("/api/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authValid), UserAuthDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("text/plain;charset=UTF-8")
                .expectBody();
    }

    @Test
    void signinInvalid() {
        client.post().uri("/api/signin", authInvalid)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authInvalid), UserAuthDto.class)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}