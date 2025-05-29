package main.src.web.controller;

import org.springframework.stereotype.Component;

@Component
public class ProjectController {

   public void getProjects() {
   System.out.println("ProjectController.getProjects()"); 
   } 
   public void getProject() {
    
       System.out.println("ProjectController.getProject()");
   }
   public void updateProject() {
   System.out.println("ProjectController.updateProject()"); 
   }
   public void deleteProject() {
    
   }

}
