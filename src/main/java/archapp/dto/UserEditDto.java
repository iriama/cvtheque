package archapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserEditDto {
    private long id;
    private String firstname;
    private String lastname;
    private String website;
    private LocalDate birthdate;
    private String password;
}
