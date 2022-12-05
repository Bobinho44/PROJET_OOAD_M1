package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PlayerImpl implements Player {

    private final List<Construction> constructions = new ArrayList<>();
    private final List<Resource> resources = new ArrayList<>();

    @Override
    public List<Construction> getConstructions() {
        return constructions;
    }

    @Override
    public void addConstruction(@NonNull Construction construction) {
        constructions.add(construction);
    }

    @Override
    public List<Resource> getResources() {
        return resources;
    }

    @Override
    public void addResource(@NonNull Resource resource) {
        resources.add(resource);
    }

    @Override
    public void removeResource(@NonNull Resource resource) {
        resources.remove(resource);
    }

}
