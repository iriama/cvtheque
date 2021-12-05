package archapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserInviteDto {
    @NotBlank(message = "BLANK")
    @Length(min = 2, max = 16, message = "LENGTH_2_16")
    private String firstname;

    @NotBlank(message = "BLANK")
    @Length(min = 2, max = 16, message = "LENGTH_2_16")
    private String lastname;

    @NotBlank(message = "BLANK")
    @Email(message = "FORMAT")
    private String email;

    @NotBlank(message = "BLANK")
    @Length(min = 8, max = 30, message = "LENGTH_8_40")
    private String password;
}
