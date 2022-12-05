package fr.univnantes.alma.core.player;

import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.List;

public interface Player {

    List<Construction> getConstructions();

    void addConstruction(@NonNull Construction construction);

    List<Resource> getResources();

    void addResource(@NonNull Resource resource);

    void removeResource(@NonNull Resource resource);

}
