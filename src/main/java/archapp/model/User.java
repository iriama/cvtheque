package archapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @Temporal(TemporalType.DATE) private Date birthdate;
    @NotNull private String password;
    @OneToMany(mappedBy = "owner") private Set<Activity> activities = new HashSet<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
