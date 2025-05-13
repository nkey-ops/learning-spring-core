package main.annotations;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SelfReference {

    @Autowired
    public SelfReference selfReferences;

    @Override
    public String toString() {
        return "SelfReference hash:" + hashCode();
    }
}
