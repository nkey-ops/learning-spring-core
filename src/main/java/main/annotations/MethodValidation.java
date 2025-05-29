package main.annotations;

import jakarta.validation.constraints.Size;

import main.validation.NonWeirdStringConstrainer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Component
@Validated
public class MethodValidation {
    private String name;

    public void setName(@NonWeirdStringConstrainer @Size(max = 1) String big) {
        System.out.println(big);
    }

    @Bean
    public static MethodValidationPostProcessor methodValidationPostProcessor() {
        var m = new MethodValidationPostProcessor();
        m.setAdaptConstraintViolations(true);
        return m;
    }

    @Bean
    public static LocalValidatorFactoryBean localFactory() {
        return new LocalValidatorFactoryBean();
    }
}
