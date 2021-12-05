package archapp.model;

import archapp.enumeration.ActivityType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Entity
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull private String firstname;
    @NotNull private String lastname;

    @NotNull private String email;

    private String website;
    private LocalDate birthdate;

    @NotNull private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "owner", orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();

    @Transient
    private int age;
    @Transient
    private List<String> professionalTitles;

    @PostLoad
    public void calculate() {
        activities.sort( (a, b) -> b.getYear() == a.getYear() && a.getUpdatedOn() != null && b.getUpdatedOn() != null ? b.getUpdatedOn().compareTo(a.getUpdatedOn()) : b.getYear() > a.getYear() ? 1 : -1 );
        if (birthdate != null) setAge(
                (int)ChronoUnit.YEARS.between(birthdate, LocalDate.now())
        );
        professionalTitles = new ArrayList<>();
        for (Activity activity: activities) {
            if (activity.getType() == ActivityType.PROFESSIONAL)
                professionalTitles.add(activity.getTitle());
        }
    }

    public User(String email, String password, String firstname, String lastname, String website) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.website = website;

    }
}
