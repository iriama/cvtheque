package archapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PersonDto extends PersonMinimalDto {
    private String email;
    private int age;
    private List<ActivityDto> activities;
}
