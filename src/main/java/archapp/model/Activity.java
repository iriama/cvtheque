package archapp.model;


import archapp.enumeration.ActivityType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "activity")
@NoArgsConstructor
@Data
public class Activity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;
    @NotNull private int year;
    @NotNull private ActivityType type;
    @NotNull private String title;
    private String description;
    private String website;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_activity")
    private User owner;

    public Activity(User owner, int year, ActivityType type, String title) {
        this.owner = owner;
        this.year = year;
        this.type = type;
        this.title = title;
    }
}
