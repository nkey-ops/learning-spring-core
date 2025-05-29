package main.aspect;

public interface PointcutInterface {
    public void a();
    public void b();
    public void c();
    public void d();
    public void throwRuntime();
    public void argumentive(String match1, Integer match2, Object match3);
    public String returnStr(String s, String s2); 
}
