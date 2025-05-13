package main.annotations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Greedy {
    public static class BeanClass {}

    @Bean
    public BeanClass bean1(Greedy greedy) {
        System.out.println("bean1");
        return new BeanClass();
    }

    @Bean
    public BeanClass bean2() {
        System.out.println("bean2");
        return new BeanClass();
    }

    // @Bean
    // public BeanClass bean3() {
    //     System.out.println("bean3");
    //    return new BeanClass();
    // }

    // @Autowired
    // public void setBean1(BeanClass bean1) {
    //
    // }
}
