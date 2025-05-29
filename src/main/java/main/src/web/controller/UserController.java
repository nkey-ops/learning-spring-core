package main.src.web.controller;

import org.springframework.stereotype.Component;

@Component
public class UserController {

    public void getUsers() {
        System.out.println("UserController.getUsers()");
    }

    public void getUser() {
        System.out.println("UserController.getUser()");
    }

    public void updateUser() {
        System.out.println("UserController.updateUser()");
    }

    public void deleteUser() {
        System.out.println("UserController.deleteUser()");
    }
}
