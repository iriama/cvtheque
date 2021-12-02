package archapp.service;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import archapp.exception.CustomException;
import archapp.model.User;
import archapp.repository.UserRepository;
import archapp.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String signin(String login, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
            return jwtTokenProvider.createToken(login);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid login/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getEmail());
        } else {
            throw new CustomException("Login is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String login) {
        userRepository.deleteByEmail(login);
    }

    public User search(String login) {
        User user = userRepository.findByEmail(login);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public User whoami(HttpServletRequest req) {
        return userRepository.findByEmail(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String login) {
        return jwtTokenProvider.createToken(login);
    }

}