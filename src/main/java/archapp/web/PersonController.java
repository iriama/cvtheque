package archapp.web;

import archapp.dto.UserDto;
import archapp.dto.UserMinimalDto;
import archapp.model.User;
import archapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PersonController {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @GetMapping("/persons")
    public List<UserMinimalDto> persons(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value="name", required = false) String name, @RequestParam(value="activity", required = false) String activity) {
        Page<User> result;
        if (page < 1) page = 1;
        if (name != null && !name.isEmpty())
            result = userRepository.searchByName(name, PageRequest.of(page - 1, 10));
        if(activity!=null && !activity.isEmpty())
            result = userRepository.searchByActivityTitle(activity,PageRequest.of(page-1,10));
        else
            result = userRepository.findAll(PageRequest.of(page - 1, 10));

        return result.stream().map(u -> modelMapper.map(u, UserMinimalDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/persons/{id}")
    public UserDto getUserById(@PathVariable(value="id") Long id){
        return modelMapper.map(userRepository.findUserById(id), UserDto.class);
    }
}
