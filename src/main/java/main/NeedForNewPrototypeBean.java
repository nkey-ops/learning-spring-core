package main;

import static java.util.Objects.requireNonNull;

public abstract class NeedForNewPrototypeBean {

    private PrototypeBean prototypeBean;

    public NeedForNewPrototypeBean(PrototypeBean prototypeBean) {
        this.prototypeBean = requireNonNull(prototypeBean);
    }
    
    public void superImportantOperationButNeedsNewBeanEachTime() {
        System.out.println("Retrived new prototyp: " + getPrototypeBeanNew());
    }

    public void newBeanWithAOP() {
        System.out.println("Stored prototype: " + prototypeBean);
    }

    public abstract PrototypeBean getPrototypeBeanNew();

    public static class PrototypeBean {}
}
