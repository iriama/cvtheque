package archapp.model;


import archapp.enumeration.ActivityType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.stream.Collectors;

@Entity
@Table(name = "activity")
@NoArgsConstructor
@Data
public class Activity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotNull private int year;
    @NotNull private ActivityType type;
    @NotNull private String title;
    private String description;
    private String website;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_activity")
    private User owner;

    @UpdateTimestamp
    private Date updatedOn;

    public Activity(User owner, int year, ActivityType type, String title) {
        this.owner = owner;
        this.year = year;
        this.type = type;
        this.title = title;
    }

    @PreRemove
    public void preRemove() {
        //getOwner().setActivities(getOwner().getActivities().stream().filter(a -> a.getId() == getId()).collect(Collectors.toList()));
    }
}
