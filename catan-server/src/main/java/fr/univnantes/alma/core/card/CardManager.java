package fr.univnantes.alma.core.card;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface CardManager {

    /**
     * Checks if a development card can be picked
     *
     * @param type the type
     * @return true if a development card can be picked, false otherwise
     */
    <T extends DevelopmentCard> boolean canPickDevelopmentCard(@NonNull Class<T> type);

    /**
     * Gets the development card cost
     *
     * @param type the type
     * @return the development card cost
     */
    @NonNull <T extends DevelopmentCard> List<Resource> getDevelopmentCardCost(@NonNull Class<T> type);

    /**
     * Picks a development card
     *
     * @param type the type
     * @return the development card
     */
    @NonNull <T extends DevelopmentCard> DevelopmentCard pickDevelopmentCard(@NonNull Class<T> type);

    /**
     * Picks a special card
     *
     * @param type the type
     * @return the special card
     */
    <T extends SpecialCard> @NonNull Optional<T> pickSpecialCard(@NonNull Class<T> type);

}