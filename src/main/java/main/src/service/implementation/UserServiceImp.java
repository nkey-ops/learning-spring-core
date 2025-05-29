package main.src.service.implementation;

import org.springframework.stereotype.Component;

import main.src.service.interfaces.UserService;

@Component
public class UserServiceImp implements UserService {
    public UserServiceImp() {
        System.out.println("UserServiceImp.UserServiceImp()");
    }

    public void getName() {
       System.out.println("UserService.getName()"); 
    }
   
    public void getAge() {
       System.out.println("UserService.getAge()"); 
    }

    public void getId() {
       System.out.println("UserService.getId()");
    }
}
