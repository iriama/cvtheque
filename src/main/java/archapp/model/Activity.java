package archapp.model;


import archapp.enumeration.ActivityType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
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
    @JoinColumn
    private User owner;

    @UpdateTimestamp
    private Date updatedOn;

    public Activity(User owner, int year, ActivityType type, String title) {
        this.owner = owner;
        this.year = year;
        this.type = type;
        this.title = title;
    }

}
