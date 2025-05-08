package main;

import java.io.File;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        var annonContext = new AnnotationConfigApplicationContext();
        new XmlBeanDefinitionReader(annonContext).loadBeanDefinitions("services.xml");
        
        System.out.println(annonContext);
        annonContext.refresh();
        System.out.println(annonContext);

       var petStoreBean = annonContext.getBean("petStoreService", PetStoreService.class);
       var petControllService = annonContext.getBean("petControllService", PetControllService.class);
       System.out.println(petStoreBean);
       System.out.println(petControllService);
    }
}
