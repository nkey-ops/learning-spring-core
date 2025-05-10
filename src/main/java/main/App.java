package main;

import java.io.IOException;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** Hello world! */
public class App {
    public static void main(String[] args) throws IOException {
        var ctx = new ClassPathXmlApplicationContext(new String[]{"services.xml"});
        ctx.setAllowBeanDefinitionOverriding(false);
        // new XmlBeanDefinitionReader(genericContext).loadBeanDefinitions("services.xml");

        // System.out.println("Context Refresh");
        System.out.println(ctx);

        var petStoreBean = ctx.getBean("petStoreService", PetStoreService.class);
        var petStoreBeanWithInnerBean =
                ctx.getBean("petStoreWithInnerBeanPetControll", PetStoreService.class);
        var petStoreBeanViaAliase =
                ctx.getBean("aliasForPetStore", PetStoreService.class);
        var petControllService =
                ctx.getBean("petControllService", PetControllService.class);
        var petControllCopy =
                ctx.getBean("petControllServiceCopy", PetControllService.class);
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

        var petConWithColle =
                ctx.getBean("petControllerWithCollections", PetControllService.class);
        System.out.println(petConWithColle);

        var test = ctx.getBean("test", PetControllService.class);
        System.out.println(test);
        var petControllerWithCollectionChild =
                ctx.getBean("petControlerWithCollectionChild", PetControllService.class);
        System.out.println(petControllerWithCollectionChild);

        System.out.println(ctx.getBean("lazyPetControll"));

        System.out.println(ctx.getBean("getAllBeansViaList"));

        var bean = ctx.getBean("needForNewBean", NeedForNewPrototypeBean.class);
        bean.superImportantOperationButNeedsNewBeanEachTime();
        bean.superImportantOperationButNeedsNewBeanEachTime();
        bean.superImportantOperationButNeedsNewBeanEachTime();
        bean.newBeanWithAOP();
        bean.newBeanWithAOP();
        bean.newBeanWithAOP();

        System.out.println("Closing The Context");
        ctx.close();
        System.out.println("Refreshing The Context");
        ctx.refresh();
        System.out.println("Stopping The Context");
        ctx.stop();


        ctx.registerShutdownHook();
    }
}
