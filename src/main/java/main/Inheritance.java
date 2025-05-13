package main;

public class Inheritance {

   
    public static class IncompatibleParent {

    }

    public static class Inheritor {
        private String name;


        @Override
        public String toString() {
            return "Inheritor [name=" + name + "]";
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
