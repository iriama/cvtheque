package archapp.model;

import archapp.enumeration.ActivityType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Data
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;
    private String firstname;
    private String lastname;
    @NotNull @Email private String email;
    private String website;
    @JsonIgnore
    private LocalDate birthdate;
    @JsonIgnore
    @NotNull private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "owner")
    @JsonManagedReference
    private List<Activity> activities = new ArrayList<>();

    @Transient
    private int age;
    @Transient
    private List<String> professionalTitles = new ArrayList<>();

    @PostLoad
    private void onLoad() {
        activities.sort( (a, b) -> b.getYear() - a.getYear()  );
        if (birthdate != null) setAge(Period.between(birthdate, LocalDate.now()).getYears());
        for (Activity activity: activities) {
            if (activity.getType() == ActivityType.PROFESSIONAL)
                professionalTitles.add(activity.getTitle());
        }
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public User(String email, String password, String firstname, String lastname, String website) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.website = website;

    }
}
