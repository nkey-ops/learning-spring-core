package main;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class PostProcessingBeans implements BeanPostProcessor {

    @Override
    public @Nullable Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("PostAfterProcessing: " + bean);
        return bean;
    }

    @Override
    public @Nullable Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("PostBeforeProcessing: " + bean);
        return bean;
    }

    
}
