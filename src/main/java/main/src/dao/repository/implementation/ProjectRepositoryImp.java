package main.src.dao.repository.implementation;

import org.springframework.stereotype.Component;

import main.src.dao.repository.interfaces.ProjectRepository;

@Component
public class ProjectRepositoryImp implements ProjectRepository{

    @Override
    public void findByCreator() {
        System.out.println("ProjectRepositoryImp.findByCreator()");
    }

    @Override
    public void findByDirector() {
        System.out.println("ProjectRepositoryImp.findByDirector()");
    }

    @Override
    public void findByName() {
        System.out.println("ProjectRepositoryImp.findByName()");
    }
    
}
