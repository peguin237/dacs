package ltjv.dacs.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ltjv.dacs.Validator.annotation.ValidCategoryId;
import ltjv.dacs.entity.Category;

public class ValidCategoryIdValidator implements ConstraintValidator<ValidCategoryId, Category> {
    @Override
    public boolean isValid(Category category, ConstraintValidatorContext context) {
        return category != null && category.getId() != null;
    } }
