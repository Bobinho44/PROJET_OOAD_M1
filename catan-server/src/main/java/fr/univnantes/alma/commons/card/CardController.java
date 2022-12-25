package fr.univnantes.alma.commons.card;

import fr.univnantes.alma.commons.card.development.DevelopmentCardJSON;
import fr.univnantes.alma.commons.card.special.SpecialCardJSON;
import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.commons.utils.collector.CollectorsUtils;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.core.card.CardManager;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.command.CommandManager;
import fr.univnantes.alma.core.configuration.Configuration;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.IntStream;
//TODO ajouter isSimilar
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
        return ReflectionUtils.getClassesOf(DevelopmentCard.class, "fr.univnantes.alma.commons.card.development").stream()
                .flatMap(development -> IntStream.range(0, Configuration.getCardAmount(development.getSimpleName()))
                        .mapToObj(i -> ReflectionUtils.getInstancesOf(development)))
                .collect(CollectorsUtils.toShuffledMap(DevelopmentCard::getUUID, development -> development));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<DevelopmentCardJSON> getDevelopmentCardsInformation() {
        return developments.values().stream()
                .map(developmentCard -> new DevelopmentCardJSON(developmentCard.getUUID(), developmentCard.getClass().getName()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull DevelopmentCard generateDevelopmentCard(@NonNull DevelopmentCardJSON developmentCardJSON) {
        try {
            DevelopmentCard developmentCard = (DevelopmentCard) ReflectionUtils.getInstancesOf(Class.forName(developmentCardJSON.getType()));

            return ReflectionUtils.changeObjectField(developmentCard, "uuid", developmentCardJSON.getUUID());
        }

        catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull DevelopmentCard getDevelopmentCard() throws RuntimeException {
       return developments.values().stream().findAny().orElseThrow();
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
        developments.put(developmentCard.getUUID(), developmentCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeDevelopmentCard(@NonNull DevelopmentCard developmentCard) {
        developments.remove(developmentCard.getUUID());
    }

    public void a() {
        developments.keySet().forEach(System.out::println);
        System.out.println("=============================");
        getDevelopmentCardsInformation().forEach(a -> System.out.println(a.getUUID()));
    }
    /**
     * Creates the special card deck
     *
     * @return the special card deck
     */
    private @NonNull Map<UUID, SpecialCard> createSpecialCardDeck() {
        return ReflectionUtils.getClassesOf(SpecialCard.class, "fr.univnantes.alma.commons.card.special").stream()
                .map(ReflectionUtils::getInstancesOf)
                .collect(CollectorsUtils.toShuffledMap(SpecialCard::getUUID, special -> special));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<SpecialCardJSON> getSpecialCardsInformation() {
        return specials.values().stream()
                .map(specialCard -> new SpecialCardJSON(specialCard.getUUID(), specialCard.getClass().getName()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull SpecialCard getSpecialCard(@NonNull SpecialCardJSON specialCardJSON) throws RuntimeException {
        return Optional.ofNullable(specials.get(specialCardJSON.getUUID())).orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSpecialCard(@NonNull SpecialCardJSON specialCardJSON) {
        return Optional.ofNullable(specials.get(specialCardJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useSpecialCard(@NonNull SpecialCard specialCard, @NonNull Player owner, @NonNull CommandManager commandManager) {

        if (specialCard.hasOwner()) {
            specialCard.looseEffect(commandManager, specialCard.getOwner());
        }

        specialCard.getEffect(commandManager, owner);
        specialCard.setOwner(owner);
    }

}