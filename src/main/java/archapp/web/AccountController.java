package archapp.web;

import archapp.dto.UserDto;
import archapp.model.User;
import archapp.repository.UserRepository;
import archapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/account")
    public UserDto account(HttpServletRequest request) {
        return modelMapper.map(userService.whoami(request), UserDto.class);
    }

}
