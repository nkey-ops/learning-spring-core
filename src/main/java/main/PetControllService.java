package main;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetControllService {

    private final String name;
    private List<String> names = new ArrayList<>();
    private Map<String, Object> namesAndObjects = new HashMap<>();

    private PetControllService(String name) {
        this.name = requireNonNull(name);
    }

    public PetControllService(List<String> names, Map<String, Object> namesAndObjects) {
        this.name = "default";
        this.names =requireNonNull(names);
        this.namesAndObjects = requireNonNull(namesAndObjects);
    }

    public static PetControllService create(String name) {
        return new PetControllService(name);
    }

    public static PetControllService createDefault() {
        return new PetControllService("default");
    }

    public static PetControllService createWithEcho(String echo) {
        System.out.println(echo);
        return new PetControllService(echo);
    }


    public PetControllService createCopy() {
        return  new PetControllService(this.name);
    }

    public static class NestPetControll {
        @Override
        public String toString() {
            return "NestPetControll []";
        }
    }
    public Map<String, Object> getNamesAndObjects() {
        return namesAndObjects;
    }

    public void setNamesAndObjects(Map<String, Object> namesAndObjects) {
        this.namesAndObjects = namesAndObjects;
    }

    @Override
    public String toString() {
        return "PetControllService [name=" + name + ", names=" + names + ", namesAndObjects=" + namesAndObjects + "]";
    }
}
