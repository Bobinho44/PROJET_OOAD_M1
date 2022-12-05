package fr.univnantes.alma.core.card;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

public interface CardManager {

    /**
     * Checks if a development card can be picked
     *
     * @return true if a development card can be picked, false otherwise
     */
    boolean canPickDevelopmentCard();

    /**
     * Gets the development card cost
     *
     * @return the development card cost
     */
    @NonNull List<Resource> getDevelopmentCardCost();

    /**
     * Picks a development card
     *
     * @return the development card
     */
    @NonNull DevelopmentCard pickDevelopmentCard();

    /**
     * Picks a special card
     * @param type the type
     * @return the special card
     */
    <S extends SpecialCard> @NonNull Optional<S> pickSpecialCard(@NonNull Class<S> type);

}