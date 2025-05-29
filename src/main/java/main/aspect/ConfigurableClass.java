package main.aspect;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class ConfigurableClass {

public ConfigurableClass() {
    System.out.println("ConfigurableClass.ConfigurableClass()");
}    

    public void setName() {
        
    }
}
