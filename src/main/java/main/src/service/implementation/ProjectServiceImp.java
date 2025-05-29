package main.src.service.implementation;

import org.springframework.stereotype.Component;

import main.src.service.interfaces.ProjectService;

@Component
public class ProjectServiceImp implements ProjectService{

    @Override
    public void getName() {
        System.out.println("ProjectServiceImp.getName()");
    }

    @Override
    public void getDirector() {
        System.out.println("ProjectServiceImp.getDirector()");
    }

    @Override
    public void getCreator() {
        System.out.println("ProjectServiceImp.getCreator()");
    }
}
