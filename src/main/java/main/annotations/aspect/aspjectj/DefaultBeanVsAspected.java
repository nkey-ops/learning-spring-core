package main.annotations.aspect.aspjectj;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class DefaultBeanVsAspected {

    @Component("defBea")
    public static class DefaultBean {
        public DefaultBean() {
            System.out.println(this);
        }
    }

    @Bean
    @Scope("prototype")
    public static AspectedBean name(DefaultBean db) {
        System.out.println("Call");
        AspectedBean aspectedBean = new AspectedBean();
        aspectedBean.setDefBea(db);
        return aspectedBean;
    }
}
