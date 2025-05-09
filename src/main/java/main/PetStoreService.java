package main;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetStoreService {

    private PetControllService petControllService;
    private String name ="defaultName";
    private List<String> names = new ArrayList<>();
    private Map<String, Object> namesAndObjects = new HashMap<>();
    

    public PetStoreService(PetControllService petControllService ) {
        this.petControllService = requireNonNull(petControllService);
    }

    public PetStoreService(PetControllService petControllService, String name) {
        this.petControllService = requireNonNull(petControllService);
        this.name = requireNonNull(name);;
        System.out.println("Service and Name");
    }


    public PetStoreService(String name, PetControllService petControllService) {
        this.petControllService = requireNonNull(petControllService);
        this.name = requireNonNull(name);;
        System.out.println("Name and Service");
    }

    @Override
    public String toString() {
        return "PetStoreService [petControllService=" + petControllService + ", name=" + name + "]";
    }

     
}
