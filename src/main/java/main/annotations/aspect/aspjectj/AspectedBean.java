package main.annotations.aspect.aspjectj;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import main.annotations.aspect.aspjectj.DefaultBeanVsAspected.DefaultBean;

@Configurable(autowire = Autowire.BY_TYPE )
public class AspectedBean {
    @Autowired
    DefaultBean defBea;

    public AspectedBean() {
        System.out.println(this);
    }

    public DefaultBean getDefBea() {
        return defBea;
    }

    public void setDefBea(DefaultBean defBea) {
        this.defBea = defBea;
    }

    @Override
    public String toString() {
        return "AspectedBean [defBea=" + defBea + "] " + hashCode() ;
    }


}
