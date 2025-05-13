package main.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NonClassPathScannedConf {

    @Autowired
    private Bean0 bean0;

    public static class Bean0 {
        public Bean0() {
            System.out.println(this);
        }

    }

    public static class Bean1 {
        public Bean1(Bean0 bean0) {
            System.out.println(this + " + " + bean0);
        }

    }

    public static class Bean2 {
        public Bean2(Bean1 bean1) {
            System.out.println(this + " + " + bean1);
        }
    }

    @Bean
    public Bean1 bean1() {
        return new Bean1(bean0);
    }
    
    @Bean
    public Bean2 bean2() {
        return new Bean2(bean1());
    }
}
