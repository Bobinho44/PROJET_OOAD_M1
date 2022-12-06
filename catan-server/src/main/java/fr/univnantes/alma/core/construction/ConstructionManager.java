package fr.univnantes.alma.core.construction;

import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConstructionManager {

    /**
     * Gets a constructable area
     *
     * @param uuid the constructable area uuid
     * @param type the type
     * @return the constructable area
     */
    <T extends Construction> @NonNull Optional<ConstructableArea<T>> getConstructableArea(@NonNull UUID uuid, @NonNull Class<T> type);

    /**
     * Gets a construction
     *
     * @param owner the owner
     * @param type the type
     * @return the construction
     */
    <T extends Construction> T getConstruction(@NonNull Player owner, @NonNull Class<T> type);

    /**
     * Gets a construction cost
     *
     * @param type the type
     * @return the construction cost
     */
    <T extends Construction> @NonNull List<Resource> getConstructionCost(@NonNull Class<T> type);

    /**
     * Checks if the construction is constructable
     *
     * @param area the area
     * @param construction the construction
     * @return true if the construction is constructable, false otherwise
     */
    <A extends Construction, T extends A> boolean isConstructable(@NonNull ConstructableArea<A> area, @NonNull T construction);

    /**
     * Constructs the construction on the area
     *
     * @param area the area
     * @param construction the construction
     */
    <A extends Construction, T extends A> void construct(@NonNull ConstructableArea<A> area, @NonNull T construction);

}