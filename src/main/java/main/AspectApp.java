package main;

import main.annotations.aspect.aspjectj.AspectedBean;
import main.aspect.OtherPointcuttingAspec;
import main.aspect.PointcutInterface;
import main.aspect.Pointcutting;
import main.aspect.PointcuttingAspect;
import main.src.dao.repository.interfaces.ProjectRepository;
import main.src.dao.repository.interfaces.UserRepository;
import main.src.service.interfaces.ProjectService;
import main.src.service.interfaces.UserService;
import main.src.web.controller.ProjectController;
import main.src.web.controller.UserController;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

// @EnableAspectJAutoProxy
@Configuration
public class AspectApp {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();

        ctx.register(AspectApp.class);
        ctx.scan("main.annotations.aspect.aspjectj");

        ctx.refresh();

        System.out.println(Arrays.toString(ctx.getBeanDefinitionNames()));
        new AspectedBean();

    }

    private static void bigMatch() {
        var ctx = new AnnotationConfigApplicationContext();

        ctx.register(AspectApp.class);
        ctx.scan("main.src");
        ctx.register(PointcuttingAspect.class);
        ctx.refresh();

        var userService = ctx.getBean(UserService.class);
        var userRepo = ctx.getBean(UserRepository.class);
        var userController = ctx.getBean(UserController.class);
        var projectService = ctx.getBean(ProjectService.class);
        var projectRepo = ctx.getBean(ProjectRepository.class);
        var projectController = ctx.getBean(ProjectController.class);

        userService.getAge();
        projectService.getName();
        // userController.getUser();
        // userRepo.findById();
    }

    private static void visibilityAccess() {
        var ctx = new AnnotationConfigApplicationContext();

        ctx.register(AspectApp.class);
        ctx.register(Pointcutting.class);
        ctx.register(PointcuttingAspect.class);
        ctx.register(OtherPointcuttingAspec.class);

        ctx.refresh();

        var bean = ctx.getBean(PointcutInterface.class);

        bean.a();
        bean.b();
        bean.c();
        bean.d();
    }
}
