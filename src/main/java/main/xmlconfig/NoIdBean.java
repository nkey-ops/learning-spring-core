package main.xmlconfig;

public class NoIdBean {

   public static class RequireNoIdBean {
       public RequireNoIdBean(NoIdBean noIdBean) {
           System.out.println("NoIdBean.RequireNonIdBean.RequireNonIdBean() " +  noIdBean);
       }
   }
}
