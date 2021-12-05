package archapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserAuthDto {
    @NotBlank(message = "BLANK")
    @Email(message = "FORMAT")
    private String email;

    @NotBlank(message = "BLANK")
    private String password;
}