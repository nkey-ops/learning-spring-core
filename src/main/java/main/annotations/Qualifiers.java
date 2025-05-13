package main.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
public class Qualifiers {

    @Component
    public static class BeanA {
        {
            System.out.println(this);
        }

    }

    // @Bean
    // @Qualifier("beanA")
    public BeanA beanA() {
        System.out.println("create a");
        return new BeanA();
    }

    // @Bean
    // @Qualifier("beanB")
    public BeanA beanB() {
        System.out.println("create b");
        return new BeanA();
    }

    // @Autowired(required = false)
    public void setBeans(@Qualifier("beanA") List<BeanA> beans) {

        System.out.println(beans);
    }
}
