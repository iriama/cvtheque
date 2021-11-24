package archapp;

import archapp.enumeration.ActivityType;
import archapp.model.Activity;
import archapp.model.User;
import archapp.repository.ActivityRepository;
import archapp.repository.UserRepository;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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
        User user1 = new User("amairi.hatem@gmail.com", "test123");
        Activity activity1 = new Activity(user1, 2020, ActivityType.PERSONAL, "projet jee");

        userRepository.save(user1);
        activityRepository.save(activity1);
    }

}
