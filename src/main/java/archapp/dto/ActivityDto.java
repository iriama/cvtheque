package archapp.dto;

import archapp.enumeration.ActivityType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.Constraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ActivityDto {
    private Long id;

    @NotBlank(message = "BLANK")
    @Length(min = 4, max = 16, message = "LENGTH_4_16")
    private String title;

    @Length(min = 10, max = 3000, message = "LENGTH_10_3000")
    private String description;

    @NotNull(message = "BLANK")
    private ActivityType type;

    @URL(message = "FORMAT")
    private String website;

    @Min(value = 1900, message = "MIN_1900")
    @Max(value = 2100, message = "MAX_2100")
    private int year;
}
