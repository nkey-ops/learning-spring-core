package main;

import main.annotations.ConversionDate;
import main.annotations.ConvertedDate;
import main.annotations.ExtendingQualifiers;
import main.annotations.FormattStrings;
import main.annotations.FormattStrings.WeirdString;
import main.annotations.MethodValidation;
import main.annotations.PetControllService;
import main.annotations.Profiled;
import main.annotations.SpelWithBeans;
import main.annotations.Validation;
import main.model.request.validation.PersonRequestValidator;
import main.validation.NonWeirdStringConstrainer;
import main.xmlconfig.NoIdBean;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
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
import org.springframework.expression.Expression;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.validation.method.MethodValidationException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// @PropertySource("application.properties")
@Configuration
@ComponentScan(includeFilters = @Filter(pattern = "") )
public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        var ctx = new ClassPathXmlApplicationContext("utils.xml");
        ctx.refresh();

        System.out.println(Arrays.toString(ctx.getBeanDefinitionNames()));
        System.out.println(ctx.getBean("myField"));
        System.out.println(ctx.getBean("java.sql.Connection.TRANSACTION_SERIALIZABLE"));
        System.out.println(ctx.getBean("better-stat"));
    }


    private static void spelCofig() {
        class Demo {
            public List<String> list;
        }
        var conf = new SpelParserConfiguration(true,true);
        var spel = new SpelExpressionParser(conf);

        Demo rootObject = new Demo();
        var str = spel.parseExpression("list[3]").getValue(rootObject);

        System.out.println(rootObject.list);
    }


    private static void getSimple() {
        class Simple {
            public List<Boolean> bools = new ArrayList<>(List.of(false, false, false));
            public int i = 10;
        }

         var spel = new SpelExpressionParser();
         var ctx = SimpleEvaluationContext.forReadWriteDataBinding().build();;
         var simple = new Simple();
         spel.parseExpression("bools[0]").setValue(ctx, simple, "true");
         spel.parseExpression("i").setValue(ctx, simple, "20");


         System.out.println(simple.bools);
         System.out.println(simple.i);
    }
    

    private static void spelPropertyAccess() {
        class TestObject {
            private String name;
            private int age;
            private String[] usernames;

            public TestObject(String name, int age, String[] usernames) {
                this.name = name;
                this.age = age;
                this.usernames = usernames;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String[] getUsernames() {
                return usernames;
            }

            public void setUsernames(String[] usernames) {
                this.usernames = usernames;
            }

        }

        var spel = new SpelExpressionParser();

        var obj = new TestObject("Test", 5, new String[]{"name1", "name2"});
        var ex = spel.parseExpression("name");;

        var name =  ex.getValue(obj, String.class);
        var age =  spel.parseExpression("age").getValue(obj, String.class);
        var names =  spel.parseExpression("usernames").getValue(obj, String[].class);
        System.out.println(name + " " + age + " " + Arrays.toString( names));
    }

    private static void simpleSpel() {
        var spel = new SpelExpressionParser();
        var expression = spel.parseExpression("'Hello World'");
        System.out.println(expression.getValue());
        expression = spel.parseExpression("'Hello World'.concat('!!!')");
        System.out.println(expression.getValue());

        expression = spel.parseExpression("'Hello World'.bytes");
        System.out.println(Arrays.toString((byte[])expression.getValue()));

        expression = spel.parseExpression("'Hello World'.bytes.length");
        System.out.println(expression.getValue());

        expression = spel.parseExpression("new String('Hi From The Constructor')");
        System.out.println(expression.getValue());
    }
    private static void methodValidation() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.registerBean(App.class);
        ctx.registerBean(MethodValidation.class);
        ctx.refresh();

        try {
            ctx.getBean(MethodValidation.class).setName("weird");
        } catch (MethodValidationException e) {
            var codes = e.getAllErrors().get(0).getCodes();
            var codes2 = e.getAllErrors().get(1).getCodes();
            System.out.println(Arrays.toString(codes));
            System.out.println(Arrays.toString(codes2));
            System.out.println(e.getAllErrors());
        }
    }

    private static void validation() {
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

    // @EventListener(
    //         classes = {
    //             ContextStartedEvent.class,
    //             ContextRefreshedEvent.class,
    //             ContextStoppedEvent.class,
    //             ContextClosedEvent.class,
    //             ApplicationEvent.class
    //         })
    // public void name(ApplicationEvent e) {
    //
    //     System.out.println(e);
    // }

    @Bean("messageSource")
    public MessageSource messageSource() {
        var m = new ResourceBundleMessageSource();
        m.setBasename("messages");
        m.setBasename("validation-messages");
        return m;
    }

    // @Bean
    // public static LocalValidatorFactoryBean localFactory() {
    //     return new LocalValidatorFactoryBean();
    // }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        var propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setLocation(
                new ClassPathResource("application.properties"));
        return propertySourcesPlaceholderConfigurer;
    }
}
