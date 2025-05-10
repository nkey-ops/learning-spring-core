package main;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class PostInitAndPreDestroy {

    public static class InitializingAndDisposableBean implements InitializingBean, DisposableBean{

        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println(this + " afterPropertiesSet");
        }

        @Override
        public void destroy() throws Exception {
            System.out.println(this + " destroy");
        }
    }

    public static class PostConstructAndPreDestroyBean {

        @PostConstruct
        public void postConstruct() throws Exception {
            System.out.println(this + " postConstruct");
        }

        @PreDestroy
        public void preDestroy() throws Exception {
            System.out.println(this + " preDestory");
        }
    }

    public static class InitAndDestroyBean {

        public void init() throws Exception {
            System.out.println(this + " init");
        }

        public void destroy() throws Exception {
            System.out.println(this + " destroy");
        }
    }
}

