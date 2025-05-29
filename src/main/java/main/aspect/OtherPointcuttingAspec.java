package main.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class OtherPointcuttingAspec {

    @After("main.aspect.PointcuttingAspect.publicMethod()")
    public void afterOtherClassPublic() {
        System.out.println("after other class public a()");
    }

    @After("main.aspect.PointcuttingAspect.protectedMethod()")
    public void afterOtherClassProtected() {
        System.out.println("after other class protect b()");
    }

    @After("main.aspect.PointcuttingAspect.privateMethod()")
    public void afterOtherClassPrivate() {
        System.out.println("after other class private c()");
    }

    @After("main.aspect.PointcuttingAspect.packageMethod()")
    public void afterOtherClassPackage() {
        System.out.println("after other class packaged d()");
    }

    @After(
            "main.aspect.PointcuttingAspect.publicMethod()"
                    + " && main.aspect.PointcuttingAspect.protectedMethod()")
    public void afterOtherClassPublicAndPackage() {
        System.out.println("after other class public and protected a() and b()");
    }
}
