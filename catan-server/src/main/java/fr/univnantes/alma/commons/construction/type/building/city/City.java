package fr.univnantes.alma.commons.construction.type.building.city;

import fr.univnantes.alma.commons.annotation.ConstructionCost;
import fr.univnantes.alma.commons.annotation.ResourceInformation;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

@ConstructionCost(resources = {
        @ResourceInformation(name = "Wheat", amount = 2),
        @ResourceInformation(name = "Ore", amount = 3)
})
public class City extends Building {

    /**
     * Creates a new city
     *
     * @param owner the owner
     */
    public City(@NonNull Player owner) {
        super(owner);
    }

}