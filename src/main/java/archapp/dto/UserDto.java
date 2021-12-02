package archapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto extends UserMinimalDto {
    private int age;
    private List<ActivityDto> activities;
}
