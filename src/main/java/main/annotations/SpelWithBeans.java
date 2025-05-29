package main.annotations;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpelWithBeans {

    public SpelWithBeans(
            @Value("#{systemProperties['java.specification.version']}") String java,
            @Value("#{systemProperties['user.name']}") String user,
            @Value("#{systemProperties['java.vm.name']}") String jvm
            ) {

        System.out.println("SpelWithBeans.SpelWithBeans() " + java + " " + user + " " + jvm);
    }
}
