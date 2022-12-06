package fr.univnantes.alma.commons.construction.type.road;

import fr.univnantes.alma.commons.annotation.ConstructionCost;
import fr.univnantes.alma.commons.annotation.ResourceInformation;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

@ConstructionCost(resources = {
        @ResourceInformation(name = "Wood", amount = 1),
        @ResourceInformation(name = "Clay", amount = 1)
})
public class RoadImpl extends Road {

    /**
     * Creates a new road
     *
     * @param owner the owner
     */
    public RoadImpl(@NonNull Player owner) {
        super(owner);
    }

}