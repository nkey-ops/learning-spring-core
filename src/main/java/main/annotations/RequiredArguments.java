package main.annotations;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import main.PetStoreService;



@Component
public class RequiredArguments {

    @Autowired(required = false)
    public RequiredArguments(@Value("${required-arguments.arg-1}") Integer arg1) {
        requireNonNull(arg1);
        System.out.println(this + " " +  arg1);
    }

    @Autowired(required = false)
    public RequiredArguments(@Value("${required-arguments.arg-2}") String arg2,
           Optional<PetStoreService>  arg3) {
        requireNonNull(arg2);
        System.out.println(this + " " + arg2);
    }

}
