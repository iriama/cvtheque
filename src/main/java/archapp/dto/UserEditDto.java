package archapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserEditDto {
    private long id;

    @NotBlank(message = "BLANK")
    @Length(min = 2, max = 16, message = "LENGTH_2_16")
    private String firstname;

    @NotBlank(message = "BLANK")
    @Length(min = 2, max = 16, message = "LENGTH_2_16")
    private String lastname;

    @URL(message = "FORMAT")
    private String website;

    private LocalDate birthdate;

    @Length(min = 8, max = 30, message = "LENGTH_8_40")
    private String password;
}
