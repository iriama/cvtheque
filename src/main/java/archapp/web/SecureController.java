package archapp.web;

import archapp.dto.*;
import archapp.model.Activity;
import archapp.model.User;
import archapp.repository.ActivityRepository;
import archapp.repository.UserRepository;
import archapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/secure")
@RequiredArgsConstructor
public class SecureController {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/account")
    public UserDto account(HttpServletRequest request) {
        return modelMapper.map(userService.whoami(request), UserDto.class);
    }

    @PutMapping("/account")
    public UserDto editAccount(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody UserEditDto edited) {
        User current = userService.whoami(request);
        if (current == null || current.getId() != edited.getId()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        if (edited.getPassword() != null && !edited.getPassword().equals("")) {
            edited.setPassword( passwordEncoder.encode(edited.getPassword()) );
        } else {
            edited.setPassword(current.getPassword());
        }

        modelMapper.map(edited, current);
        UserDto body = modelMapper.map(userRepository.save(current), UserDto.class);
        response.setStatus(201);
        return body;
    }

    @DeleteMapping("/account/activity/{id}")
    public PersonDto deleteActivity(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        User current = userService.whoami(request);
        if (current == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        current.getActivities().removeIf(a -> Objects.equals(a.getId(), id));
        current.calculate(); // recalculate
        return modelMapper.map(userRepository.save(current), PersonDto.class);
    }

    @PostMapping("/account/activity")
    public PersonDto addActivity(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody ActivityDto activityDto) {
        User current = userService.whoami(request);
        if (current == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        activityDto.setId(null); // force new
        Activity activity = new Activity();
        modelMapper.map(activityDto, activity);
        activity.setOwner(current);

        current.getActivities().add(activity);
        current.calculate(); // recalculate

        return modelMapper.map(userRepository.save(current), PersonDto.class);
    }

    @PutMapping("/account/activity")
    public PersonDto editActivity(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody ActivityDto activityDto) {
        User current = userService.whoami(request);
        if (current == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        Optional<Activity> activityOpt = current.getActivities().stream().filter(a -> a.getId().equals(activityDto.getId())).findAny();
        if (!activityOpt.isPresent()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        Activity activity = activityOpt.get();
        modelMapper.map(activityDto, activity);
        activityRepository.save(activity);
        current.calculate(); // recalculate

        return modelMapper.map(current, PersonDto.class);
    }

    @PostMapping("/invite")
    public UserDto invite(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody UserInviteDto infos) {
        User current = userService.whoami(request);
        if (current == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }
        
        infos.setPassword( passwordEncoder.encode(infos.getPassword()) );
        User user = new User();
        modelMapper.map(infos, user);
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

}
