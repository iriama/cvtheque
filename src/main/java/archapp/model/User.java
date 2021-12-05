package archapp.model;

import archapp.enumeration.ActivityType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "BLANK")
    @Length(min = 2, max = 16, message = "LENGTH_2_16")
    private String firstname;

    @NotBlank(message = "BLANK")
    @Length(min = 2, max = 16, message = "LENGTH_2_16")
    private String lastname;

    @NotBlank(message = "BLANK")
    @Email(message = "FORMAT")
    private String email;

    @URL(message = "FORMAT")
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
