package main.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Aspect
public class PointcuttingAspect {

    public PointcuttingAspect() {
        System.out.println("New Instance " + this);
    }

    @Pointcut("execution(public * a(..))")
    public static void publicMethod() {}

    @Pointcut("execution(public * b(..))")
    protected static void protectedMethod() {}

    @Pointcut("execution(public * c(..))")
    private static void privateMethod() {}

    @Pointcut("execution(public * d(..))")
    static void packageMethod() {}

    @After("execution(* main.src.*.*(..))")
    public static void test() {
        System.out.println("PointcuttingAspect.test()");
    }

    @Pointcut("within(main.src..*)")
    public static void src() { }

    @Pointcut("within(main.src.web..*)")
    public static void inWebLayer() {}

    @Pointcut("within(main.src.dao..*)")
    public static void inDaoLayer() {}

    @Pointcut("within(main.src.service..*)")
    public static void inServiceLayer() {}

    @After("publicMethod()")
    public void beforePublicMethod() {
        System.out.println("After public method a()");
    }

    @After("protectedMethod()")
    public void beforeProtectedMethod() {
        System.out.println("After protected method b()");
    }

    @After("privateMethod()")
    public void beforePrivateMethod() {
        System.out.println("After private method c()");
    }

    @After("packageMethod()")
    public void beforePackageMethod() {
        System.out.println("After packaged method d()");
    }

    @After("inWebLayer()")
    public void afterInWebLayer() {
        System.out.println("PointcuttingAspect.afterInWebLayer()");
    }

    @After("inDaoLayer()")
    public void afterInDaoLayer() {
        System.out.println("PointcuttingAspect.afterInDaoLayer()");
    }

    @After( "inServiceLayer()")
    public void afterServiceLayer() {
        System.out.println("PointcuttingAspect.afterServiceLayer()");
    }

    @After("execution(* main.s*..*User*.*(..))")
    public void afterWithUser() {
        System.out.println("PointcuttingAspect.afterWithUser()");
    }

    @After("execution(* main.s*..*Service*.*(..))")
    public void afterWithService() {
        System.out.println("PointcuttingAspect.afterWithService()");
    }

    @AfterReturning(value = "execution(* main.aspect.PointcutInterface.returnStr(..))"
    , returning = "p"
    )
    public void afterReturning(String param) {
       System.out.println("PointcuttingAspect.afterReturning() " + param); 
    }

    @AfterThrowing(pointcut =  "execution(* main.aspect.PointcutInterface.throwRuntime(..))")
    public void afterThrow() {
       System.out.println("PointcuttingAspect.afterThrow()"); 
    }

    @AfterThrowing(pointcut =  "execution(* main.aspect.PointcutInterface.throwRuntime(..))",
    throwing = "ex")
    public void afterThrowThrowable(Throwable e) {
        System.out.println("PointcuttingAspect.afterThrowThrowable()" + e);
    }

    @AfterThrowing(pointcut =  "execution(* main.aspect.PointcutInterface.throwRuntime(..))",
    throwing = "ex"
    )
    public void afterThrowRuntimeException(RuntimeException e) {
        System.out.println("PointcuttingAspect.afterThrowRuntimeException()");
    }

    @AfterThrowing(pointcut =  "execution(* main.aspect.PointcutInterface.throwRuntime(..))"
    , throwing = "e"
    )
    public void afterThrowNullPointer(NullPointerException e) {
        System.out.println("PointcuttingAspect.afterThrowNullPointer()");
    }

    @Before(value = "execution(* main.aspect.PointcutInterface.argumentive(..))" 
     + " && args(a, b, c)"
    ,argNames = "a,b, c"
    )
    public void beforeArgumentive(
            String a 
            ,Integer b
            ,Object c
            ) {
       System.out.printf("PointcuttingAspect.beforeArgumentive() a: %s b: %s c: %s%n",
               a, b,""); 
    }


    @Around("execution(* main.aspect.PointcutInterface.returnStr(..))")
    public Object aroundReturnStr(ProceedingJoinPoint p) throws Throwable {
        System.out.println("PointcuttingAspect.aroundReturnStr()");
        return  p.proceed();
    }
}

