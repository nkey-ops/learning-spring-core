package main.annotations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class Profiled {

    public class Bean1 {
        public Bean1() {
            System.out.println("Profiled.Bean1.Bean1()");
        }
    }
    public class Bean2 {
        public Bean2() {
            System.out.println("Profiled.Bean2.Bean2()");
        }
    }

    @Bean
    @Profile("bean1")
    public Bean1 bean1() {
        return new Bean1();
    }

    @Bean
    @Profile("bean2")
    public Bean2 bean2() {
        return new Bean2();
    }
    
}
