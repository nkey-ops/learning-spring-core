package main.aspect;

import org.springframework.stereotype.Component;

@Component
public class Pointcutting implements PointcutInterface {

    @Override
    public void a() {
        System.out.println("a");
    }

    @Override
    public void b() {
        System.out.println("b");
    }

    @Override
    public void c() {
        System.out.println("c");
    }

    @Override
    public void d() {
        System.out.println("d");
    }

    @Override
    public void throwRuntime() {
        throw new RuntimeException("yo");
    }

    @Override
    public void argumentive(String match1, Integer match2, Object match3) {
        System.out.println("Pointcutting.argumentive() " + match1 + " " + match2 + " " + match3 );
    }

    @Override
    public String returnStr(String s, String s2) {
        System.out.println("Pointcutting.returnStr() " + s + " " + s2);
        return s;
    }

    
}
