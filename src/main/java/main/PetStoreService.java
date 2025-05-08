package main;

public class PetStoreService {

    private PetControllService petControllService;

    @Override
    public String toString() {
        return "PetStoreService [petControllService=" + petControllService + "]";
    }

    public PetControllService getPetControllService() {
        return petControllService;
    }

    public void setPetControllService(PetControllService petControllService) {
        this.petControllService = petControllService;
    }
}
