package ltjv.dacs.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ltjv.dacs.Repository.IUserRepository;
import ltjv.dacs.Validator.annotation.ValidUsername;
import ltjv.dacs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if(userRepository == null)
            return true;
        return userRepository.findByUsername(username) == null;
    }
}
