package de.geekinbusiness;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("random")
@RequestScoped
public class Randomizer {

    public String random() {
        return Double.toString(Math.random());
    }
}
