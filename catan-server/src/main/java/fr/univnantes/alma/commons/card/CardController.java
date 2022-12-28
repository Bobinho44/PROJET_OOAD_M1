package fr.univnantes.alma.commons.card;

import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.commons.utils.collector.CollectorsUtils;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.core.card.CardJSON;
import fr.univnantes.alma.core.card.CardManager;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.configuration.Configuration;
import fr.univnantes.alma.core.exception.EmptyCardDeckException;
import fr.univnantes.alma.core.exception.UndefinedCardOwnerException;
import fr.univnantes.alma.core.exception.UnregisteredCardException;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.resource.Resource;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Implementation of a card manager
 */
public class CardController implements CardManager {

    /**
     * Fields
     */
    private final Map<UUID, DevelopmentCard> developments;
    private final Map<UUID, SpecialCard> specials;

    /**
     * Creates a new card manager
     */
    public CardController() {
        this.developments = createDevelopmentCardDeck();
        this.specials = createSpecialCardDeck();
    }

    /**
     * Creates the development card deck
     *
     * @return the development card deck
     */
    private @NonNull Map<UUID, DevelopmentCard> createDevelopmentCardDeck() {
        return ReflectionUtils.getSubClassesOf(DevelopmentCard.class, "fr.univnantes.alma.commons.card.development").stream()
                .flatMap(development -> IntStream.range(0, Configuration.getCardAmount(development.getSimpleName()))
                        .mapToObj(i -> ReflectionUtils.getInstancesOf(development)))
                .collect(CollectorsUtils.toShuffledMap(DevelopmentCard::getUUID, development -> development));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<CardJSON> getDevelopmentCardsInformation() {
        return developments.values().stream()
                .map(developmentCard -> (CardJSON) new CardJSONImpl(developmentCard.getUUID(), developmentCard.getClass().getName()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull DevelopmentCard generateDevelopmentCard(@NonNull CardJSON cardJSON) throws UnregisteredCardException {
        Objects.requireNonNull(cardJSON, "cardJSON cannot be null!");

        try {
            DevelopmentCard developmentCard = (DevelopmentCard) ReflectionUtils.getInstancesOf(Class.forName(cardJSON.getType()));

            return ReflectionUtils.changeObjectField(developmentCard, "uuid", cardJSON.getUUID());
        }

        catch (ClassNotFoundException e) {
            throw new UnregisteredCardException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull DevelopmentCard getDevelopmentCard() throws EmptyCardDeckException {
       return developments.values().stream().findAny().orElseThrow(EmptyCardDeckException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDevelopmentCard() {
        return !developments.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Resource> getDevelopmentCardCost() {
        return Configuration.getDevelopmentCardCost().stream()
                .map(resource -> (Resource) new ResourceImpl(resource.getName()) {}.amount(resource.getAmount()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDevelopmentCard(@NonNull DevelopmentCard developmentCard) {
        Objects.requireNonNull(developmentCard, "developmentCard cannot be null!");

        developments.put(developmentCard.getUUID(), developmentCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeDevelopmentCard(@NonNull DevelopmentCard developmentCard) {
        Objects.requireNonNull(developmentCard, "developmentCard cannot be null!");

        developments.remove(developmentCard.getUUID());
    }

    /**
     * Creates the special card deck
     *
     * @return the special card deck
     */
    private @NonNull Map<UUID, SpecialCard> createSpecialCardDeck() {
        return ReflectionUtils.getSubClassesOf(SpecialCard.class, "fr.univnantes.alma.commons.card.special").stream()
                .map(ReflectionUtils::getInstancesOf)
                .collect(CollectorsUtils.toShuffledMap(SpecialCard::getUUID, special -> special));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<CardJSON> getSpecialCardsInformation() {
        return specials.values().stream()
                .map(specialCard -> (CardJSON) new CardJSONImpl(specialCard.getUUID(), specialCard.getClass().getName()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull SpecialCard getSpecialCard(@NonNull CardJSON cardJSON) throws UnregisteredCardException {
        Objects.requireNonNull(cardJSON, "cardJSON cannot be null!");

        return Optional.ofNullable(specials.get(cardJSON.getUUID())).orElseThrow(UnregisteredCardException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSpecialCard(@NonNull CardJSON cardJSON) {
        Objects.requireNonNull(cardJSON, "cardJSON cannot be null!");

        return Optional.ofNullable(specials.get(cardJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Player getSpecialCardOwner(@NonNull SpecialCard specialCard) throws UndefinedCardOwnerException {
        Objects.requireNonNull(specialCard, "specialCard cannot be null!");

        return specialCard.getOwner().orElseThrow(UndefinedCardOwnerException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull boolean hasSpecialCardOwner(@NonNull SpecialCard specialCard) {
        Objects.requireNonNull(specialCard, "specialCard cannot be null!");

        return specialCard.getOwner().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useSpecialCard(@NonNull SpecialCard specialCard, @NonNull Player owner) {
        Objects.requireNonNull(specialCard, "specialCard cannot be null!");
        Objects.requireNonNull(owner, "owner cannot be null!");

        specialCard.useEffect(owner);
    }

}