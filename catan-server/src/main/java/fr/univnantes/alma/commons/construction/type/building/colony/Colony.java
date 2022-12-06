package fr.univnantes.alma.commons.construction.type.building.colony;

import fr.univnantes.alma.commons.annotation.ConstructionCost;
import fr.univnantes.alma.commons.annotation.ResourceInformation;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.construction.type.Building;
import org.springframework.lang.NonNull;

@ConstructionCost(resources = {
        @ResourceInformation(name = "Wood", amount = 1),
        @ResourceInformation(name = "Clay", amount = 1),
        @ResourceInformation(name = "Wheat", amount = 1),
        @ResourceInformation(name = "Wool", amount = 1)
})
public class Colony extends Building {

    /**
     * Creates a new colony
     *
     * @param owner the owner
     */
    public Colony(@NonNull Player owner) {
        super(owner);
    }

}