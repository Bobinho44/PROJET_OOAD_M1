package fr.univnantes.alma.core.card;

import fr.univnantes.alma.commons.card.development.DevelopmentCardJSON;
import fr.univnantes.alma.commons.card.special.SpecialCardJSON;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.command.CommandManager;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Interface representing a card manager
 */
public interface CardManager {

    /**
     * Gets the development cards information
     *
     * @return the development cards information
     */
    @NonNull List<DevelopmentCardJSON> getDevelopmentCardsInformation();

    /**
     * Generates the development card
     *
     * @param developmentCardJSON the development card information
     * @return the development card
     */
    @NonNull DevelopmentCard generateDevelopmentCard(@NonNull DevelopmentCardJSON developmentCardJSON);

    /**
     * Gets a development card
     *
     * @return the development card
     * @throws RuntimeException if there is no development card
     */
    @NonNull DevelopmentCard getDevelopmentCard() throws RuntimeException;

    /**
     * Checks if a development card exist
     *
     * @return true if a development card exist, false otherwise
     */
    boolean hasDevelopmentCard();

    /**
     * Gets the development card cost
     *
     * @return the development card cost
     */
    @NonNull List<Resource> getDevelopmentCardCost();

    /**
     * Adds the development card
     *
     * @param developmentCard the development card
     */
    void addDevelopmentCard(@NonNull DevelopmentCard developmentCard);

    /**
     * Removes the development card
     *
     * @param developmentCard the development card
     */
    void removeDevelopmentCard(@NonNull DevelopmentCard developmentCard);

    /**
     * Gets the special cards information
     *
     * @return the special cards information
     */
    @NonNull List<SpecialCardJSON> getSpecialCardsInformation();

    /**
     * Gets the special card
     *
     * @param specialCardJSON the special card
     * @return the special card
     * @throws RuntimeException if the special card does not exist
     */
    @NonNull SpecialCard getSpecialCard(@NonNull SpecialCardJSON specialCardJSON) throws RuntimeException;

    /**
     * Checks if the special card exist
     *
     * @param specialCardJSON the special card
     * @return true if the special card exist, false otherwise
     */
    boolean hasSpecialCard(@NonNull SpecialCardJSON specialCardJSON);

    /**
     * Uses the special card
     *
     * @param specialCard    the special card
     * @param owner          the new owner
     * @param commandManager the command manager
     */
    void useSpecialCard(@NonNull SpecialCard specialCard, @NonNull Player owner, @NonNull CommandManager commandManager);

}