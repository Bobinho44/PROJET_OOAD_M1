package fr.univnantes.alma.core.card;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.exception.EmptyCardDeckException;
import fr.univnantes.alma.core.exception.UndefinedCardOwnerException;
import fr.univnantes.alma.core.exception.UnregisteredCardException;
import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.resource.IResource;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Interface representing a card manager
 */
public interface ICardManager {

    /**
     * Gets the development cards information
     *
     * @return the development cards information
     */
    @NonNull List<ICardJSON> getDevelopmentCardsInformation();

    /**
     * Generates the development card
     *
     * @param cardJSON the development card information
     * @return the development card
     * @throws UnregisteredCardException if the development card does not exist
     */
    @NonNull DevelopmentCard generateDevelopmentCard(@NonNull ICardJSON cardJSON) throws UnregisteredCardException;

    /**
     * Gets a development card
     *
     * @return the development card
     * @throws EmptyCardDeckException if there is no development card
     */
    @NonNull DevelopmentCard getDevelopmentCard() throws EmptyCardDeckException;

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
    @NonNull List<IResource> getDevelopmentCardCost();

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
    @NonNull List<ICardJSON> getSpecialCardsInformation();

    /**
     * Gets the special card
     *
     * @param cardJSON the special card information
     * @return the special card
     * @throws UnregisteredCardException if the special card does not exist
     */
    @NonNull SpecialCard getSpecialCard(@NonNull ICardJSON cardJSON) throws UnregisteredCardException;

    /**
     * Checks if the special card exist
     *
     * @param cardJSON the special card information
     * @return true if the special card exist, false otherwise
     */
    boolean hasSpecialCard(@NonNull ICardJSON cardJSON);

    /**
     * Gets the special card owner
     *
     * @param specialCard the special card
     * @return the special card owner
     * @throws UndefinedCardOwnerException if the owner does not exist
     */
    @NonNull
    IPlayer getSpecialCardOwner(@NonNull SpecialCard specialCard) throws UndefinedCardOwnerException;

    /**
     * Checks if the special has an owner
     *
     * @param specialCard the special card
     * @return true if the special has an owner, false otherwise
     */
    boolean hasSpecialCardOwner(@NonNull SpecialCard specialCard);

    /**
     * Uses the special card
     *
     * @param specialCard    the special card
     * @param owner          the new owner
     */
    void useSpecialCard(@NonNull SpecialCard specialCard, @NonNull IPlayer owner);

}