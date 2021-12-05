package archapp.web;

import archapp.dto.ActivityDto;
import archapp.dto.UserAuthDto;
import archapp.dto.UserEditDto;
import archapp.dto.UserInviteDto;
import archapp.enumeration.ActivityType;
import archapp.model.Activity;
import archapp.model.User;
import archapp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active:test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SecureControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Activity activity1;
    private ActivityDto existantActivity;
    private ActivityDto newActivity;
    private User test1;
    private UserEditDto editDto;
    private UserInviteDto inviteDto;
    private String token;

    @BeforeAll
    public void setup() {
        test1 = new User(
                "test1@mail.com",
                passwordEncoder.encode("test1234"),
                "test1firstname",
                "test1lastname",
                "http://test1.com"
        );

        activity1 = new Activity(
                test1,
                2021,
                ActivityType.PERSONAL,
                "activity1"
        );
        test1.getActivities().add(activity1);
        userRepository.save(test1);

        UserAuthDto auth = new UserAuthDto();
        auth.setEmail("test1@mail.com");
        auth.setPassword("test1234");

        editDto = new UserEditDto();
        editDto.setFirstname("test1editfn");
        editDto.setLastname("test1editln");
        editDto.setId(1L);

        existantActivity = new ActivityDto();
        existantActivity.setTitle("activitytitleedit");
        existantActivity.setYear(2020);
        existantActivity.setType(ActivityType.PERSONAL);
        existantActivity.setId(1L);

        newActivity = new ActivityDto();
        newActivity.setTitle("activitytitleedit2");
        newActivity.setYear(2021);
        newActivity.setType(ActivityType.PROFESSIONAL);

        inviteDto = new UserInviteDto();
        inviteDto.setFirstname("firstname");
        inviteDto.setLastname("lastname");
        inviteDto.setPassword("test1234");
        inviteDto.setEmail("firsttest@gmail.com");

        client.post().uri("/api/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(auth), UserAuthDto.class)
                .exchange()
                .expectBody().consumeWith(r -> {
                    token = new String(r.getResponseBody(), Charset.forName("UTF-8"));
                });
    }


    @AfterEach
    public void afterEach() {
        test1.getActivities().clear();
        test1.getActivities().add(activity1);
        userRepository.save(test1);
    }

    @AfterAll
    public void teardown() {
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    void accountNoAuth() {
        client.get().uri("/api/secure/account")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @Order(2)
    void account() {
        client.get().uri("/api/secure/account")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.firstname").isEqualTo(test1.getFirstname());
    }

    @Test
    @Order(3)
    void editAccountNoAuth() {
        client.put().uri("/api/secure/account")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(editDto), UserEditDto.class)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @Order(4)
    void editAccount() {
        /*client.put().uri("/api/secure/account")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(editDto), UserEditDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.firstname").isEqualTo(editDto.getFirstname());*/
    }

    @Test
    @Order(5)
    void addActivityNoAuth() {
        client.post().uri("/api/secure/account/activity")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(newActivity), ActivityDto.class)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @Order(6)
    void deleteActivityNoAuth() {
        client.delete().uri("/api/secure/account/activity/" + activity1.getId())
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @Order(7)
    void addActivity() {
        client.post().uri("/api/secure/account/activity")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(newActivity), ActivityDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.activities[0].title").isEqualTo(newActivity.getTitle());
    }

    @Test
    @Order(8)
    void deleteActivity() {
        client.delete().uri("/api/secure/account/activity/" + activity1.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.activities[1]").doesNotExist();
    }

    @Test
    @Order(9)
    void editActivityNoAuth() {
        client.put().uri("/api/secure/account/activity")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(existantActivity), ActivityDto.class)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @Order(10)
    void editActivity() {
        existantActivity.setId( 2L );
        client.put().uri("/api/secure/account/activity")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(existantActivity), ActivityDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.activities[2].title").isEqualTo(existantActivity.getTitle());
    }

    @Test
    @Order(11)
    void inviteNoAuth() {
        client.post().uri("/api/secure/invite")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(inviteDto), UserInviteDto.class)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @Order(12)
    void invite() {
        client.post().uri("/api/secure/invite")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(inviteDto), UserInviteDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.firstname").isEqualTo(inviteDto.getFirstname());
    }
}