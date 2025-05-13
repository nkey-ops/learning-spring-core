package main;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.ClassPathResource;

import main.annotations.PetControllService;
import main.annotations.Profiled;

@PropertySource("application.properties")
@Configuration
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        // var ctx = new ClassPathXmlApplicationContext("config-resolver.xml");

        messaged();
    }

    private static void messaged() {
        var ctx = new AnnotationConfigApplicationContext(App.class);
        var ms = ctx.getBean(MessageSource.class);

        System.out.println(ms.getMessage("super-bad-error", new Object[]{new NullPointerException("Ouch")}, Locale.UK));;
    }

    private static void propertySourced() {
        var ctx = new AnnotationConfigApplicationContext();

        var propertySource = new MapPropertySource("custom-source", Map.of("spring.profiles.active", "bean1"));
        ctx.getEnvironment().getPropertySources().addFirst(propertySource);
        ctx.register(Profiled.class);
        ctx.refresh();

        System.out.println(ctx);
    }

    private static void profiled() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(Profiled.class);
        ctx.getEnvironment().setActiveProfiles("bean2");
        ctx.refresh();

        System.out.println(ctx);
    }

    private static void xmlContext() {
        var ctx = new ClassPathXmlApplicationContext(new String[] {"services.xml"}, true);
        ctx.setAllowBeanDefinitionOverriding(false);

        var petStoreBean = ctx.getBean("petStoreService", PetStoreService.class);
        var petStoreBeanWithInnerBean =
                ctx.getBean("petStoreWithInnerBeanPetControll", PetStoreService.class);
        var petStoreBeanViaAliase = ctx.getBean("aliasForPetStore", PetStoreService.class);
        var petControllService = ctx.getBean("petControllService", PetControllService.class);
        var petControllCopy = ctx.getBean("petControllServiceCopy", PetControllService.class);
        var nestPetControll =
                ctx.getBean("nestPetControll", PetControllService.NestPetControll.class);
        System.out.println(petStoreBean);
        System.out.println(petStoreBeanViaAliase);
        System.out.println(petStoreBeanWithInnerBean);
        System.out.println(petControllService);
        System.out.println(petControllCopy);
        System.out.println(nestPetControll);

        var listableBeanFactory =
                ((DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory());
        listableBeanFactory.registerSingleton(
                NonRegisteredBean.class.getName(), NonRegisteredBean.class);
        var nonRegBean = ctx.getBean(NonRegisteredBean.class.getName());
        System.out.println(nonRegBean);

        var beanFactory = ctx.getBeanFactory();
        System.out.println(beanFactory);
        System.out.println(beanFactory.getType("petControllService"));

        var petConWithColle = ctx.getBean("petControllerWithCollections", PetControllService.class);
        System.out.println(petConWithColle);

        var test = ctx.getBean("test", PetControllService.class);
        System.out.println(test);
        var petControllerWithCollectionChild =
                ctx.getBean("petControlerWithCollectionChild", PetControllService.class);
        System.out.println(petControllerWithCollectionChild);

        System.out.println(ctx.getBean("lazyPetControll"));

        System.out.println(ctx.getBean("getAllBeansViaList"));

        var neadForNewBean = ctx.getBean("needForNewBean", NeedForNewPrototypeBean.class);
        neadForNewBean.superImportantOperationButNeedsNewBeanEachTime();
        neadForNewBean.superImportantOperationButNeedsNewBeanEachTime();
        neadForNewBean.superImportantOperationButNeedsNewBeanEachTime();
        neadForNewBean.newBeanWithAOP();
        neadForNewBean.newBeanWithAOP();
        neadForNewBean.newBeanWithAOP();

        System.out.println(ctx.getBean("inheritor").toString());

        System.out.println("Stopping The Context");
        ctx.stop();

        System.out.println("Refreshing The Context");
        ctx.refresh();

        System.out.println("Closing The Context");
        ctx.close();

        ctx.registerShutdownHook();
    }

    @Bean("messageSource")
    public MessageSource messageSource() {
       var m = new ResourceBundleMessageSource();
       m.setBasename("messages");
       return m; 
    }

    // @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        var propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setLocation(new ClassPathResource("application.properties"));
        propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);


        return propertySourcesPlaceholderConfigurer;
    }
}
