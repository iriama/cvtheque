package archapp.validation;

import archapp.repository.UserRepository;
import archapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && email.length() > 0 && !userService.exist(email);
    }
}
