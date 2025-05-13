package main;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;

import main.annotations.PetControllService;

public class GetAllBeansNow {

    private List<PetControllService> beanList;
    private Map<String,PetControllService> beanMap;

    public GetAllBeansNow(List<PetControllService> beanList) {
        this.beanList = requireNonNull(beanList);
    }

    public GetAllBeansNow(Map<String, PetControllService> beanMap) {
        this.beanMap = requireNonNull(beanMap);
    }

    @Override
    public String toString() {
        return "GetAllBeansNow [beanList=" + beanList + ", beanMap=" + beanMap + "]";
    }
}
