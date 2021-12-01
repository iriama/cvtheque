package archapp.web;

import archapp.dto.UserAuthDto;
import archapp.model.User;
import archapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/signin")
    public String login(@RequestBody UserAuthDto user) {
        return userService.signin(user.getEmail(), user.getPassword());
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserAuthDto user) {
        return userService.signup(modelMapper.map(user, User.class));
    }

}
