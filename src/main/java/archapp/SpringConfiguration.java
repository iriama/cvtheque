package archapp;

import archapp.enumeration.ActivityType;
import archapp.model.Activity;
import archapp.model.User;
import archapp.repository.ActivityRepository;
import archapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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

    @Autowired private UserRepository userRepository;
    @Autowired private ActivityRepository activityRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void afterStartup() {
        User user1 = new User("amairi.hatem@gmail.com", "test123","Amairi","hatem","https://google.com");
        User user2 = new User("aziz2512@gmail.com", "test13","Aziz","ndiaye","ndiaye.com");
        User user3 = new User("amairi.ndiaye@gmail.com", "test1","essaie","tester","notresite.com");
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


        for (int i = 0; i<100; i++) {
            userRepository.save(new User(
                    "user" + i + "gmail.com",
                    "password" + i,
                    "Firstname" + i,
                    "Lastname" + i,
                    "https://site" + i + ".com"
            ));
        }
    }

}
