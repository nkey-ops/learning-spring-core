package main.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NonWeierdStringConstrainerValidator
        implements ConstraintValidator<NonWeirdStringConstrainer, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return !value.contains("weird");
    }
}
