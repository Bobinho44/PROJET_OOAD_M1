package fr.univnantes.alma.core.territory;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.*;

public interface TerritoryManager {

    /**
     * Gets the territory
     *
     * @param uuid the territory uuid
     * @return the optional territory
     */
    @NonNull Optional<Territory> getTerritory(@NonNull UUID uuid);

    /**
     * Gets the thief territory
     *
     * @return the optional thief territory
     */
    @NonNull Optional<Territory> getThiefTerritory();

    /**
     * Moves the thief
     *
     * @param territory the new territory
     */
    void moveThief(@NonNull Territory territory);

    /**
     * Gets all territories loot
     * @param number the token number
     *
     * @return all territories loot
     */
    @NonNull List<Map.Entry<Player, Resource>> getLoot(int number);

}