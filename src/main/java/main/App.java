package main;

import main.annotations.ConversionDate;
import main.annotations.ConvertedDate;
import main.annotations.FormattStrings;
import main.annotations.FormattStrings.WeirdString;
import main.annotations.PetControllService;
import main.annotations.Profiled;
import main.annotations.Validation;
import main.model.request.validation.PersonRequestValidator;
import main.validation.NonWeirdStringConstrainer;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

// @PropertySource("application.properties")
@Configuration
public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        // var ctx = new ClassPathXmlApplicationContext("config-resolver.xml");

        var ctx = new AnnotationConfigApplicationContext();
        ctx.scan("main.validation");
        ctx.register(App.class);
        ctx.refresh();
        var validator = ctx.getBean(Validator.class);

        class TestObject {
            @NonWeirdStringConstrainer public String constrainedString;
            public String nonConstrainedString;

            @Override
            public String toString() {
                return "TestObject [constrainedString="
                        + constrainedString
                        + ", nonConstrainedString="
                        + nonConstrainedString
                        + "]";
            }

            public String getConstrainedString() {
                return constrainedString;
            }

            public void setConstrainedString(String constrainedString) {
                this.constrainedString = constrainedString;
            }

            public String getNonConstrainedString() {
                return nonConstrainedString;
            }

            public void setNonConstrainedString(String nonConstrainedString) {
                this.nonConstrainedString = nonConstrainedString;
            }
        }

        var obj = new TestObject();
        var dataBinder = new DataBinder(obj);
        var props =
                new MutablePropertyValues(
                        Map.<String, String>of(
                                "constrainedString", "weird", "nonConstrainedString", "weird"));

        dataBinder.setValidator(validator);
        dataBinder.bind(props);
        dataBinder.validate();

        System.out.println(obj);
        System.out.println(dataBinder.getBindingResult());
        System.out.println(obj);
    }

    private static void defaultFormatting() {
        class DateObject {
            private LocalDate date;

            public LocalDate getDate() {
                return date;
            }

            public void setDate(LocalDate date) {
                this.date = date;
            }
        }

        var date = new DateObject();
        var dataBinder = new DataBinder(date);
        var conversionService = new DefaultFormattingConversionService();

        // var dateTimeFormatterRegistrar = new DateTimeFormatterRegistrar();
        // dateTimeFormatterRegistrar.setDateFormatter(DateTimeFormatter.ofPattern(""));

        dataBinder.setConversionService(conversionService);
        dataBinder.bind(new MutablePropertyValues(Map.<String, String>of("date", "12.13.52")));

        System.out.println(date.date);
        System.out.println(dataBinder.getBindingResult());
        System.out.println(date.date);
    }

    private static void formatting() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.registerBean(App.class);
        ctx.registerBean(FormattStrings.class);
        ctx.refresh();

        class TestObject {
            @WeirdString public String wierd;
            public String wierdButNotMarked;

            public String getWierd() {
                return wierd;
            }

            public void setWierd(String wierd) {
                this.wierd = wierd;
            }

            public String getWierdButNotMarked() {
                return wierdButNotMarked;
            }

            public void setWierdButNotMarked(String wierdButNotMarked) {
                this.wierdButNotMarked = wierdButNotMarked;
            }

            @Override
            public String toString() {
                return "TestObject [wierd="
                        + wierd
                        + ", wierdButNotMarked="
                        + wierdButNotMarked
                        + "]";
            }
        }
        var service = ctx.getBean(ConversionService.class);
        var object = new TestObject();
        var binder = new DataBinder(object);
        binder.setConversionService(service);
        var bindProps =
                new MutablePropertyValues(
                        Map.<String, String>of(
                                "wierd", "that's a weird string",
                                "wierdButNotMarked", "that's a weird string"));
        binder.bind(bindProps);

        System.out.println(object);
        System.out.println(binder.getBindingResult());
        ;
        System.out.println(object);
    }

    private static void conversionService() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.registerBean(App.class);
        ctx.registerBean(ConversionDate.class);
        ctx.refresh();
    }

    private static void converDate() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.registerBean(App.class);
        ctx.register(ConvertedDate.class);
        ctx.refresh();
    }

    private static void datePropertyEditor() {
        var ctx = new AnnotationConfigApplicationContext();

        ctx.registerBean(App.class);

        class DateEditor extends CustomDateEditor {
            public DateEditor() {
                super(new SimpleDateFormat("yyyy-MM-dd"), false);
            }
        }
        ;

        ctx.getBeanFactory().registerCustomEditor(Date.class, DateEditor.class);

        ctx.registerBean(ConvertedDate.class);
        ctx.refresh();
    }

    private static void beanWrapped() {
        var ctx = new AnnotationConfigApplicationContext(App.class);

        var w = new BeanWrapperImpl();
        w.setBeanInstance(new Validation.Person());

        System.out.println(Arrays.toString(w.getPropertyDescriptors()));
        System.out.println(w.getPropertyType("name") + " : " + w.getPropertyValue("name"));
        System.out.println(
                w.getPropertyTypeDescriptor("name") + " : " + w.getPropertyDescriptor("name"));

        System.out.println(w);
    }

    private static void validate() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(App.class);
        ctx.register(Validation.class);
        ctx.register(PersonRequestValidator.class);
        ctx.refresh();

        var val = ctx.getBean(Validation.class);
        var person = new Validation.Person();
        person.setAge(200);
        person.setName("ver-incompatible$$");
        val.print(person);
    }

    private static void customEvents() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(App.class);
        ctx.refresh();
        System.out.println(Arrays.toString(ctx.getBeanDefinitionNames()));
        ;

        var m = ctx.getBean(SimpleApplicationEventMulticaster.class);
        // m.setTaskExecutor(Executors.newCachedThreadPool());

        System.out.println("publishing");
        m.multicastEvent(new ApplicationEvent("message") {});
        System.out.println("published");
    }

    private static void bluntEvents() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(App.class);
        ctx.refresh();

        ctx.stop();
        ctx.start();
        ctx.close();
    }

    private static void messaged() {
        var ctx = new AnnotationConfigApplicationContext(App.class);
        var ms = ctx.getBean(MessageSource.class);

        System.out.println(
                ms.getMessage(
                        "super-bad-error",
                        new Object[] {new NullPointerException("Ouch")},
                        Locale.UK));
        ;
    }

    private static void propertySourced() {
        var ctx = new AnnotationConfigApplicationContext();

        var propertySource =
                new MapPropertySource("custom-source", Map.of("spring.profiles.active", "bean1"));
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

    @EventListener(
            classes = {
                ContextStartedEvent.class,
                ContextRefreshedEvent.class,
                ContextStoppedEvent.class,
                ContextClosedEvent.class,
                ApplicationEvent.class
            })
    public void name(ApplicationEvent e) {

        System.out.println(e);
    }

    @Bean("messageSource")
    public MessageSource messageSource() {
        var m = new ResourceBundleMessageSource();
        m.setBasename("messages");
        m.setBasename("validation-messages");
        return m;
    }

    @Bean
    public static LocalValidatorFactoryBean localFactory() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        var propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setLocation(
                new ClassPathResource("application.properties"));
        return propertySourcesPlaceholderConfigurer;
    }
}
