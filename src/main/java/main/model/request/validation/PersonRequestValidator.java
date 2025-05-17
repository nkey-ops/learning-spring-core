package main.model.request.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import main.annotations.Validation;
import main.annotations.Validation.Person;

public class PersonRequestValidator implements Validator {
    public PersonRequestValidator() {
        System.out.println("PersonRequestValidator.PersonRequestValidator()");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Validation.Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty");

        var p = (Person) target;

        if(p.getName().length() < 3) {
            errors.rejectValue("name", "name.lessThan", new Object[]{p.getName(), 3}, "");
        }else if(p.getName().length() > 15) {
            errors.rejectValue("name", "name.moreThan", new Object[]{p.getName(), 15}, "");
        }

        if(!p.getName().matches("[a-zA-z][a-zA-Z0-9]+")) {
            errors.rejectValue("name", "name.notMatch", new Object[]{p.getName(), "[a-zA-z][a-zA-Z0-9]+"}, "");
        }

        if(p.getAge() < 0) {
            errors.rejectValue("age", "age.belowZero");
        } 
        if(p.getAge() > 150) {
            errors.rejectValue("age", "age.moreThan", new Object[]{150}, "");
        }
    }
}
