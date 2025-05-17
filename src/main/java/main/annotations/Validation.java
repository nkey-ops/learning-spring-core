package main.annotations;

import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

public class Validation {

    private Map<String, Validator> validators;
    private MessageSource messageSource;

    public static class Person {
        private String name;
        private String email;
        private int age;

        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        @Override
        public String toString() {
            return "Person [name=" + name + ", email=" + email + ", age=" + age + "]";
        }
    
    }

    public Validation(Map<String, Validator> validators, MessageSource messageSource) {
        this.validators = validators;
        this.messageSource = messageSource;
        System.out.println("Validation.Validation()");
    }
    
    public void print(Person person) {
        for (var entry :validators.entrySet()) {
            if(entry.getValue().supports(Person.class)) {

                var errors = new BeanPropertyBindingResult(person, "request.person");
                entry.getValue().validate(person, errors);;

                for (var error : errors.getAllErrors()) {
              System.out.println(messageSource.getMessage(error.getObjectName() + "." + error.getCode(), error.getArguments(), Locale.US));;
                }
            }
        }

        System.out.println("Validation.print()" + person);
    }
}
