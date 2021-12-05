package archapp.web;

import archapp.dto.UserAuthDto;
import archapp.model.User;
import archapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
