package archapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PersonMinimalDto {
    private long id;
    private String firstname;
    private String lastname;
    private String website;
    private List<String> professionalTitles;
}
