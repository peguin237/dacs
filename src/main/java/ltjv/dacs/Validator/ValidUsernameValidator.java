package ltjv.dacs.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ltjv.dacs.Validator.annotation.ValidUsername;
import ltjv.dacs.service.UserService;

@RequiredArgsConstructor
public class ValidUsernameValidator implements
        ConstraintValidator<ValidUsername, String> {
    private final UserService userService;
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return userService.findByUsername(username).isEmpty();
    } }
