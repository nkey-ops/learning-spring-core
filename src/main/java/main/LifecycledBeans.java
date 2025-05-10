package main;

import org.springframework.context.Lifecycle;
import org.springframework.context.LifecycleProcessor;
import org.springframework.context.SmartLifecycle;

public class LifecycledBeans {

    public static class LifecycleProcessedBean implements Lifecycle {

        @Override
        public void start() {
            System.out.println(this + " start");
        }

        @Override
        public void stop() {
            System.out.println(this + " stop");
        }

        @Override
        public boolean isRunning() {
            System.out.println(this + " isRunning");
            return true;
        }

        // @Override
        // public void onRefresh() {
        //     System.out.println(this + " onRefresh");
        // }
        //
        // @Override
        // public void onClose() {
        //     System.out.println(this + " onClose");
        // }
    }

    public static class SmartcycleBean implements SmartLifecycle {
        private volatile boolean isRunning;

        public void init() {
            
            System.out.println(this + " init");
        }
        public void destroy() {
            System.out.println(this + " destroy");
            
        }

        @Override
        public void start() {
            System.out.println(this + " start");
            isRunning = true;
        }

        @Override
        public void stop() {
            System.out.println(this + " stop");
            isRunning = false;
        }

        @Override
        public boolean isRunning() {
            System.out.println(this + " isRunning");
            return isRunning;
        }

        @Override
        public boolean isAutoStartup() {
            System.out.println(this + " isAutoStartup");
            // return SmartLifecycle.super.isAutoStartup();
            return true;
        }

        @Override
        public void stop(Runnable callback) {
            System.out.println(this + " stopCallBack");
            SmartLifecycle.super.stop(callback);
        }
        @Override
        public int getPhase() {
            return -100;
        }

    
    }
}
