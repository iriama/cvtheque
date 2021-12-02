package archapp.dto;

import archapp.enumeration.ActivityType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivityDto {
    private long id;
    private String title;
    private String description;
    private ActivityType type;
    private String website;
    private int year;
}
