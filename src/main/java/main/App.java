package main;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

/** Hello world! */
public class App {
    public static void main(String[] args) throws IOException {
        var genericContext = new GenericApplicationContext();
        genericContext.setAllowBeanDefinitionOverriding(false);
        new XmlBeanDefinitionReader(genericContext).loadBeanDefinitions("services.xml");

        genericContext.refresh();
        System.out.println(genericContext);

        var petStoreBean = genericContext.getBean("petStoreService", PetStoreService.class);
        var petStoreBeanWithInnerBean =
                genericContext.getBean("petStoreWithInnerBeanPetControll", PetStoreService.class);
        var petStoreBeanViaAliase =
                genericContext.getBean("aliasForPetStore", PetStoreService.class);
        var petControllService =
                genericContext.getBean("petControllService", PetControllService.class);
        var petControllCopy =
                genericContext.getBean("petControllServiceCopy", PetControllService.class);
        var nestPetControll =
                genericContext.getBean("nestPetControll", PetControllService.NestPetControll.class);
        System.out.println(petStoreBean);
        System.out.println(petStoreBeanViaAliase);
        System.out.println(petStoreBeanWithInnerBean);
        System.out.println(petControllService);
        System.out.println(petControllCopy);
        System.out.println(nestPetControll);

        var listableBeanFactory =
                ((DefaultListableBeanFactory) genericContext.getAutowireCapableBeanFactory());
        listableBeanFactory.registerSingleton(
                NonRegisteredBean.class.getName(), NonRegisteredBean.class);
        var nonRegBean = genericContext.getBean(NonRegisteredBean.class.getName());
        System.out.println(nonRegBean);

        var beanFactory = genericContext.getBeanFactory();
        System.out.println(beanFactory);
        System.out.println(beanFactory.getType("petControllService"));

        var petConWithColle =
                genericContext.getBean("petControllerWithCollections", PetControllService.class);
        System.out.println(petConWithColle);

        var test = genericContext.getBean("test", PetControllService.class);
        System.out.println(test);
        var petControllerWithCollectionChild =
                genericContext.getBean("petControlerWithCollectionChild", PetControllService.class);
        System.out.println(petControllerWithCollectionChild);

        System.out.println(genericContext.getBean("lazyPetControll"));

    }
}
