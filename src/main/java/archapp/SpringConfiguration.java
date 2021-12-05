package archapp;

import archapp.enumeration.ActivityType;
import archapp.model.Activity;
import archapp.model.User;
import archapp.repository.ActivityRepository;
import archapp.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
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

    final private UserRepository userRepository;
    final private ActivityRepository activityRepository;
    final private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    @Profile("dev")
    public void afterStartup() {

        User user1 = new User("amairi.hatem@gmail.com", passwordEncoder.encode("test1234"),"Hatem","Amairi","https://github.com/iriama");
        User user2 = new User("aziz2512@gmail.com", passwordEncoder.encode("test1234"),"Ndiaye","Aziz","https://github.com/n-abdoulAziz");

        Activity activity1 = new Activity(user1, 2020, ActivityType.PERSONAL, "Jeu démineur");
        Activity activity2 = new Activity(user1, 2021, ActivityType.PROFESSIONAL, "Ingénieur DevOps");
        Activity activity3 = new Activity(user1, 2020, ActivityType.PROFESSIONAL, "Développeur RPA");
        Activity activity4 = new Activity(user1, 2017, ActivityType.OTHER, "Hackathon");
        Activity activity5 = new Activity(user1, 2020, ActivityType.EDUCATIONAL, "Projet JEE");

        activity3.setDescription("Développement de solution d'automatisation intelligentes (Chabot/RPA/IA)");
        activity3.setWebsite("https://france.devoteam.com/");
        user1.setBirthdate(LocalDate.parse("1997-06-10"));

        userRepository.save(user1);
        userRepository.save(user2);
        activityRepository.save(activity1);
        activityRepository.save(activity2);
        activityRepository.save(activity3);
        activityRepository.save(activity4);
        activityRepository.save(activity5);


        // GENERATED
        userRepository.save(new User("donna.turner@yahoo.fr", passwordEncoder.encode("test1234"), "Donna", "Turner", "https://donna-turner.com"));
        userRepository.save(new User("kimberly.bell@yahoo.fr", passwordEncoder.encode("test1234"), "Kimberly", "Bell", "https://kimberly-bell.com"));
        userRepository.save(new User("abigail.rutherford@yahoo.fr", passwordEncoder.encode("test1234"), "Abigail", "Rutherford", "https://abigail-rutherford.com"));
        userRepository.save(new User("thomas.hunter@yahoo.fr", passwordEncoder.encode("test1234"), "Thomas", "Hunter", "https://thomas-hunter.com"));
        userRepository.save(new User("sarah.mcgrath@yahoo.fr", passwordEncoder.encode("test1234"), "Sarah", "McGrath", "https://sarah-mcgrath.com"));
        userRepository.save(new User("victor.miller@yahoo.fr", passwordEncoder.encode("test1234"), "Victor", "Miller", "https://victor-miller.com"));
        userRepository.save(new User("sam.payne@yahoo.fr", passwordEncoder.encode("test1234"), "Sam", "Payne", "https://sam-payne.com"));
        userRepository.save(new User("rachel.macleod@yahoo.fr", passwordEncoder.encode("test1234"), "Rachel", "MacLeod", "https://rachel-macleod.com"));
        userRepository.save(new User("john.walsh@yahoo.fr", passwordEncoder.encode("test1234"), "John", "Walsh", "https://john-walsh.com"));
        userRepository.save(new User("amanda.peake@yahoo.fr", passwordEncoder.encode("test1234"), "Amanda", "Peake", "https://amanda-peake.com"));
        userRepository.save(new User("alexander.roberts@yahoo.fr", passwordEncoder.encode("test1234"), "Alexander", "Roberts", "https://alexander-roberts.com"));
        userRepository.save(new User("madeleine.newman@yahoo.fr", passwordEncoder.encode("test1234"), "Madeleine", "Newman", "https://madeleine-newman.com"));
        userRepository.save(new User("una.payne@yahoo.fr", passwordEncoder.encode("test1234"), "Una", "Payne", "https://una-payne.com"));
        userRepository.save(new User("andrew.roberts@yahoo.fr", passwordEncoder.encode("test1234"), "Andrew", "Roberts", "https://andrew-roberts.com"));
        userRepository.save(new User("thomas.paige@yahoo.fr", passwordEncoder.encode("test1234"), "Thomas", "Paige", "https://thomas-paige.com"));
        userRepository.save(new User("paul.white@yahoo.fr", passwordEncoder.encode("test1234"), "Paul", "White", "https://paul-white.com"));
        userRepository.save(new User("una.roberts@yahoo.fr", passwordEncoder.encode("test1234"), "Una", "Roberts", "https://una-roberts.com"));
        userRepository.save(new User("jonathan.langdon@yahoo.fr", passwordEncoder.encode("test1234"), "Jonathan", "Langdon", "https://jonathan-langdon.com"));
        userRepository.save(new User("andrew.metcalfe@yahoo.fr", passwordEncoder.encode("test1234"), "Andrew", "Metcalfe", "https://andrew-metcalfe.com"));
        userRepository.save(new User("diana.sharp@yahoo.fr", passwordEncoder.encode("test1234"), "Diana", "Sharp", "https://diana-sharp.com"));
        userRepository.save(new User("carol.stewart@yahoo.fr", passwordEncoder.encode("test1234"), "Carol", "Stewart", "https://carol-stewart.com"));
        userRepository.save(new User("deirdre.greene@yahoo.fr", passwordEncoder.encode("test1234"), "Deirdre", "Greene", "https://deirdre-greene.com"));
        userRepository.save(new User("kylie.springer@yahoo.fr", passwordEncoder.encode("test1234"), "Kylie", "Springer", "https://kylie-springer.com"));
        userRepository.save(new User("eric.quinn@yahoo.fr", passwordEncoder.encode("test1234"), "Eric", "Quinn", "https://eric-quinn.com"));
        userRepository.save(new User("claire.allan@yahoo.fr", passwordEncoder.encode("test1234"), "Claire", "Allan", "https://claire-allan.com"));
        userRepository.save(new User("sally.mills@yahoo.fr", passwordEncoder.encode("test1234"), "Sally", "Mills", "https://sally-mills.com"));
        userRepository.save(new User("olivia.scott@yahoo.fr", passwordEncoder.encode("test1234"), "Olivia", "Scott", "https://olivia-scott.com"));
        userRepository.save(new User("thomas.berry@yahoo.fr", passwordEncoder.encode("test1234"), "Thomas", "Berry", "https://thomas-berry.com"));
        userRepository.save(new User("nicholas.mathis@yahoo.fr", passwordEncoder.encode("test1234"), "Nicholas", "Mathis", "https://nicholas-mathis.com"));
        userRepository.save(new User("ella.kelly@yahoo.fr", passwordEncoder.encode("test1234"), "Ella", "Kelly", "https://ella-kelly.com"));
        userRepository.save(new User("zoe.paterson@yahoo.fr", passwordEncoder.encode("test1234"), "Zoe", "Paterson", "https://zoe-paterson.com"));
        userRepository.save(new User("katherine.newman@yahoo.fr", passwordEncoder.encode("test1234"), "Katherine", "Newman", "https://katherine-newman.com"));
        userRepository.save(new User("sebastian.hamilton@yahoo.fr", passwordEncoder.encode("test1234"), "Sebastian", "Hamilton", "https://sebastian-hamilton.com"));
        userRepository.save(new User("anna.cornish@yahoo.fr", passwordEncoder.encode("test1234"), "Anna", "Cornish", "https://anna-cornish.com"));
        userRepository.save(new User("simon.avery@yahoo.fr", passwordEncoder.encode("test1234"), "Simon", "Avery", "https://simon-avery.com"));
        userRepository.save(new User("andrea.randall@yahoo.fr", passwordEncoder.encode("test1234"), "Andrea", "Randall", "https://andrea-randall.com"));
        userRepository.save(new User("chloe.anderson@yahoo.fr", passwordEncoder.encode("test1234"), "Chloe", "Anderson", "https://chloe-anderson.com"));
        userRepository.save(new User("bernadette.abraham@yahoo.fr", passwordEncoder.encode("test1234"), "Bernadette", "Abraham", "https://bernadette-abraham.com"));
        userRepository.save(new User("jason.edmunds@yahoo.fr", passwordEncoder.encode("test1234"), "Jason", "Edmunds", "https://jason-edmunds.com"));
        userRepository.save(new User("isaac.lawrence@yahoo.fr", passwordEncoder.encode("test1234"), "Isaac", "Lawrence", "https://isaac-lawrence.com"));
        userRepository.save(new User("lauren.welch@yahoo.fr", passwordEncoder.encode("test1234"), "Lauren", "Welch", "https://lauren-welch.com"));
        userRepository.save(new User("grace.quinn@yahoo.fr", passwordEncoder.encode("test1234"), "Grace", "Quinn", "https://grace-quinn.com"));
        userRepository.save(new User("wanda.gibson@yahoo.fr", passwordEncoder.encode("test1234"), "Wanda", "Gibson", "https://wanda-gibson.com"));
        userRepository.save(new User("olivia.arnold@yahoo.fr", passwordEncoder.encode("test1234"), "Olivia", "Arnold", "https://olivia-arnold.com"));
        userRepository.save(new User("emily.turner@yahoo.fr", passwordEncoder.encode("test1234"), "Emily", "Turner", "https://emily-turner.com"));
        userRepository.save(new User("ella.anderson@yahoo.fr", passwordEncoder.encode("test1234"), "Ella", "Anderson", "https://ella-anderson.com"));
        userRepository.save(new User("lauren.sanderson@yahoo.fr", passwordEncoder.encode("test1234"), "Lauren", "Sanderson", "https://lauren-sanderson.com"));
        userRepository.save(new User("jake.mitchell@yahoo.fr", passwordEncoder.encode("test1234"), "Jake", "Mitchell", "https://jake-mitchell.com"));
        userRepository.save(new User("samantha.graham@yahoo.fr", passwordEncoder.encode("test1234"), "Samantha", "Graham", "https://samantha-graham.com"));
        userRepository.save(new User("william.north@yahoo.fr", passwordEncoder.encode("test1234"), "William", "North", "https://william-north.com"));
        userRepository.save(new User("lillian.lawrence@yahoo.fr", passwordEncoder.encode("test1234"), "Lillian", "Lawrence", "https://lillian-lawrence.com"));
        userRepository.save(new User("carol.grant@yahoo.fr", passwordEncoder.encode("test1234"), "Carol", "Grant", "https://carol-grant.com"));
        userRepository.save(new User("colin.nash@yahoo.fr", passwordEncoder.encode("test1234"), "Colin", "Nash", "https://colin-nash.com"));
        userRepository.save(new User("dominic.chapman@yahoo.fr", passwordEncoder.encode("test1234"), "Dominic", "Chapman", "https://dominic-chapman.com"));
        userRepository.save(new User("phil.james@yahoo.fr", passwordEncoder.encode("test1234"), "Phil", "James", "https://phil-james.com"));
        userRepository.save(new User("amy.howard@yahoo.fr", passwordEncoder.encode("test1234"), "Amy", "Howard", "https://amy-howard.com"));
        userRepository.save(new User("grace.dickens@yahoo.fr", passwordEncoder.encode("test1234"), "Grace", "Dickens", "https://grace-dickens.com"));
        userRepository.save(new User("anna.payne@yahoo.fr", passwordEncoder.encode("test1234"), "Anna", "Payne", "https://anna-payne.com"));
        userRepository.save(new User("warren.berry@yahoo.fr", passwordEncoder.encode("test1234"), "Warren", "Berry", "https://warren-berry.com"));
        userRepository.save(new User("gordon.pullman@yahoo.fr", passwordEncoder.encode("test1234"), "Gordon", "Pullman", "https://gordon-pullman.com"));
        userRepository.save(new User("brandon.welch@yahoo.fr", passwordEncoder.encode("test1234"), "Brandon", "Welch", "https://brandon-welch.com"));
        userRepository.save(new User("colin.clark@yahoo.fr", passwordEncoder.encode("test1234"), "Colin", "Clark", "https://colin-clark.com"));
        userRepository.save(new User("wendy.fisher@yahoo.fr", passwordEncoder.encode("test1234"), "Wendy", "Fisher", "https://wendy-fisher.com"));
        userRepository.save(new User("carol.ogden@yahoo.fr", passwordEncoder.encode("test1234"), "Carol", "Ogden", "https://carol-ogden.com"));
        userRepository.save(new User("peter.peters@yahoo.fr", passwordEncoder.encode("test1234"), "Peter", "Peters", "https://peter-peters.com"));
        userRepository.save(new User("bernadette.mathis@yahoo.fr", passwordEncoder.encode("test1234"), "Bernadette", "Mathis", "https://bernadette-mathis.com"));
        userRepository.save(new User("julian.jones@yahoo.fr", passwordEncoder.encode("test1234"), "Julian", "Jones", "https://julian-jones.com"));
        userRepository.save(new User("john.roberts@yahoo.fr", passwordEncoder.encode("test1234"), "John", "Roberts", "https://john-roberts.com"));
        userRepository.save(new User("pippa.macdonald@yahoo.fr", passwordEncoder.encode("test1234"), "Pippa", "MacDonald", "https://pippa-macdonald.com"));
        userRepository.save(new User("frank.mackay@yahoo.fr", passwordEncoder.encode("test1234"), "Frank", "Mackay", "https://frank-mackay.com"));
        userRepository.save(new User("una.fisher@yahoo.fr", passwordEncoder.encode("test1234"), "Una", "Fisher", "https://una-fisher.com"));
        userRepository.save(new User("kevin.harris@yahoo.fr", passwordEncoder.encode("test1234"), "Kevin", "Harris", "https://kevin-harris.com"));
        userRepository.save(new User("pippa.hodges@yahoo.fr", passwordEncoder.encode("test1234"), "Pippa", "Hodges", "https://pippa-hodges.com"));
        userRepository.save(new User("joseph.fraser@yahoo.fr", passwordEncoder.encode("test1234"), "Joseph", "Fraser", "https://joseph-fraser.com"));
        userRepository.save(new User("dylan.howard@yahoo.fr", passwordEncoder.encode("test1234"), "Dylan", "Howard", "https://dylan-howard.com"));
        userRepository.save(new User("jasmine.nolan@yahoo.fr", passwordEncoder.encode("test1234"), "Jasmine", "Nolan", "https://jasmine-nolan.com"));
        userRepository.save(new User("samantha.clark@yahoo.fr", passwordEncoder.encode("test1234"), "Samantha", "Clark", "https://samantha-clark.com"));
        userRepository.save(new User("jake.powell@yahoo.fr", passwordEncoder.encode("test1234"), "Jake", "Powell", "https://jake-powell.com"));
        userRepository.save(new User("peter.mackay@yahoo.fr", passwordEncoder.encode("test1234"), "Peter", "Mackay", "https://peter-mackay.com"));
        userRepository.save(new User("madeleine.scott@yahoo.fr", passwordEncoder.encode("test1234"), "Madeleine", "Scott", "https://madeleine-scott.com"));
        userRepository.save(new User("tracey.short@yahoo.fr", passwordEncoder.encode("test1234"), "Tracey", "Short", "https://tracey-short.com"));
        userRepository.save(new User("ruth.sanderson@yahoo.fr", passwordEncoder.encode("test1234"), "Ruth", "Sanderson", "https://ruth-sanderson.com"));
        userRepository.save(new User("ian.payne@yahoo.fr", passwordEncoder.encode("test1234"), "Ian", "Payne", "https://ian-payne.com"));
        userRepository.save(new User("ella.ross@yahoo.fr", passwordEncoder.encode("test1234"), "Ella", "Ross", "https://ella-ross.com"));
        userRepository.save(new User("lauren.piper@yahoo.fr", passwordEncoder.encode("test1234"), "Lauren", "Piper", "https://lauren-piper.com"));
        userRepository.save(new User("gordon.ball@yahoo.fr", passwordEncoder.encode("test1234"), "Gordon", "Ball", "https://gordon-ball.com"));
        userRepository.save(new User("justin.smith@yahoo.fr", passwordEncoder.encode("test1234"), "Justin", "Smith", "https://justin-smith.com"));
        userRepository.save(new User("joseph.mackenzie@yahoo.fr", passwordEncoder.encode("test1234"), "Joseph", "Mackenzie", "https://joseph-mackenzie.com"));
        userRepository.save(new User("alexandra.hardacre@yahoo.fr", passwordEncoder.encode("test1234"), "Alexandra", "Hardacre", "https://alexandra-hardacre.com"));
        userRepository.save(new User("jason.mackenzie@yahoo.fr", passwordEncoder.encode("test1234"), "Jason", "Mackenzie", "https://jason-mackenzie.com"));
        userRepository.save(new User("luke.morgan@yahoo.fr", passwordEncoder.encode("test1234"), "Luke", "Morgan", "https://luke-morgan.com"));
        userRepository.save(new User("sarah.langdon@yahoo.fr", passwordEncoder.encode("test1234"), "Sarah", "Langdon", "https://sarah-langdon.com"));
        userRepository.save(new User("dominic.duncan@yahoo.fr", passwordEncoder.encode("test1234"), "Dominic", "Duncan", "https://dominic-duncan.com"));
        userRepository.save(new User("leonard.simpson@yahoo.fr", passwordEncoder.encode("test1234"), "Leonard", "Simpson", "https://leonard-simpson.com"));
        userRepository.save(new User("pippa.gray@yahoo.fr", passwordEncoder.encode("test1234"), "Pippa", "Gray", "https://pippa-gray.com"));
        userRepository.save(new User("warren.hodges@yahoo.fr", passwordEncoder.encode("test1234"), "Warren", "Hodges", "https://warren-hodges.com"));
        userRepository.save(new User("molly.clarkson@yahoo.fr", passwordEncoder.encode("test1234"), "Molly", "Clarkson", "https://molly-clarkson.com"));
        userRepository.save(new User("felicity.murray@yahoo.fr", passwordEncoder.encode("test1234"), "Felicity", "Murray", "https://felicity-murray.com"));
        userRepository.save(new User("yvonne.greene@yahoo.fr", passwordEncoder.encode("test1234"), "Yvonne", "Greene", "https://yvonne-greene.com"));
        userRepository.save(new User("amanda.clarkson@yahoo.fr", passwordEncoder.encode("test1234"), "Amanda", "Clarkson", "https://amanda-clarkson.com"));
        userRepository.save(new User("natalie.mills@yahoo.fr", passwordEncoder.encode("test1234"), "Natalie", "Mills", "https://natalie-mills.com"));
        userRepository.save(new User("madeleine.ball@yahoo.fr", passwordEncoder.encode("test1234"), "Madeleine", "Ball", "https://madeleine-ball.com"));
        userRepository.save(new User("megan.metcalfe@yahoo.fr", passwordEncoder.encode("test1234"), "Megan", "Metcalfe", "https://megan-metcalfe.com"));
    }

}
