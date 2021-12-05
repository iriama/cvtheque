package archapp;

import archapp.enumeration.ActivityType;
import archapp.model.Activity;
import archapp.model.User;
import archapp.repository.ActivityRepository;
import archapp.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Configuration
public class SpringConfiguration extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Starter.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }

    @Bean
    public ObjectMapper jacksonMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Autowired private UserRepository userRepository;
    @Autowired private ActivityRepository activityRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void afterStartup() {
        User user1 = new User("amairi.hatem@gmail.com", passwordEncoder.encode("test123"),"hatem","Amairi","https://google.com");
        User user2 = new User("aziz2512@gmail.com", passwordEncoder.encode("test13"),"ndiaye","Aziz","http://ndiaye.com");
        User user3 = new User("amairi.ndiaye@gmail.com", passwordEncoder.encode("test1"),"essaie","tester","http:// notresite.com");
        Activity activity1 = new Activity(user1, 2020, ActivityType.PERSONAL, "projet jee");
        Activity activity2 = new Activity(user1, 2019, ActivityType.PROFESSIONAL, "Ingénieur fullstack");
        Activity activity3 = new Activity(user1, 2020, ActivityType.PROFESSIONAL, "Ingénieur spring");
        Activity activity4 = new Activity(user1, 2020, ActivityType.OTHER, "Can21");
        Activity activity5 = new Activity(user1, 2020, ActivityType.EDUCATIONAL, "Amu21");
        activity3.setDescription("L'informaticien assure le bon fonctionnement des outils informatiques");
        activity3.setWebsite("google.com");
        user1.setBirthdate(LocalDate.parse("1997-06-10"));
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        activityRepository.save(activity1);
        activityRepository.save(activity2);
        activityRepository.save(activity3);
        activityRepository.save(activity4);
        activityRepository.save(activity5);

        /*for (int i = 0; i<100; i++) {
            userRepository.save(new User(
                    "user" + i + "gmail.com",
                    passwordEncoder.encode("password" + i),
                    "Firstname" + i,
                    "Lastname" + i,
                    "https://site" + i + ".com"
            ));
        }*/
    }

}
