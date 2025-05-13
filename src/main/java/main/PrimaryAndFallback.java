package main;

import static java.util.Objects.requireNonNull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Fallback;
import org.springframework.context.annotation.Primary;

@Configuration
public class PrimaryAndFallback {

    public static class PrimaryAndFallbackBean {
        private String name;

        public PrimaryAndFallbackBean(String name) {
            this.name = requireNonNull(name);
        }

        @Override
        public String toString() {
            return "PrimaryAndFallbackBean [name=" + name + "]";
        }
    }

    public static class BeanConsumer {}

    // @Bean
    @Primary
    public PrimaryAndFallbackBean primary() {
        return new PrimaryAndFallbackBean("primary");
    }

    @Bean
    @Fallback
    public PrimaryAndFallbackBean fallback() {
        return new PrimaryAndFallbackBean("falback");
    }

    // @Bean
    public PrimaryAndFallbackBean regular() {
        return new PrimaryAndFallbackBean("regular");
    }

    @Bean
    public BeanConsumer consumer(PrimaryAndFallbackBean bean) {
        requireNonNull(bean);
        System.out.println(bean);
        return new BeanConsumer();
    }
}
