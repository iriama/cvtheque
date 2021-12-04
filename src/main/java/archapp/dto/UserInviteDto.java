package archapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInviteDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
