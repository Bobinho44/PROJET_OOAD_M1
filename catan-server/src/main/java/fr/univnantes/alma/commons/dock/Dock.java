package fr.univnantes.alma.commons.dock;

import fr.univnantes.alma.core.ressource.Resource;

public class Dock {
    private final Integer ratio;
    private final Resource resource;


    public Dock(Integer ratio, Resource resource) {
        this.ratio = ratio;
        this.resource = resource;
    }

    public Integer getRatio() {
        return ratio;
    }

    public Resource getResource() {
        return resource;
    }

}
