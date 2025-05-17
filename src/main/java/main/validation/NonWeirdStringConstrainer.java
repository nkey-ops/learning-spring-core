package main.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NonWeierdStringConstrainerValidator.class)
public @interface NonWeirdStringConstrainer {
    String message() default "The field is too wierd";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
