package archapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

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