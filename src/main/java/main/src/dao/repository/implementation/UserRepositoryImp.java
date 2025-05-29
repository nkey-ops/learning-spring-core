package main.src.dao.repository.implementation;

import org.springframework.stereotype.Component;

import main.src.dao.repository.interfaces.UserRepository;

@Component
public class UserRepositoryImp implements UserRepository{

    @Override
    public void findById() {
        System.out.println("UserRepositoryImp.findById()");
    }

    @Override
    public void findByName() {
        System.out.println("UserRepositoryImp.findByName()");
    }

    @Override
    public void findByAge() {
        System.out.println("UserRepositoryImp.findByAge()");
    }

    
}
