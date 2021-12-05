package archapp.web;

import archapp.dto.UserAuthDto;
import archapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signin")
    public String login(@Valid @RequestBody UserAuthDto user) {
        return userService.signin(user.getEmail(), user.getPassword());
    }
}
