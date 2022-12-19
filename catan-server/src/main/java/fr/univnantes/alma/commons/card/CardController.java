package fr.univnantes.alma.commons.card;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.annotation.DevelopmentCardCost;
import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.commons.utils.collector.CollectorsUtils;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.commons.utils.stream.IndexedStream;
import fr.univnantes.alma.core.card.CardManager;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.IntStream;

public class CardController implements CardManager {

    /**
     * Fields
     */
    private final Stack<DevelopmentCard> developments;
    private final List<SpecialCard> specials;

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
    private @NonNull Stack<DevelopmentCard> createDevelopmentCardDeck() {
        return ReflectionUtils.getClassesOf(DevelopmentCard.class, "fr.univnantes.alma.commons.card.development").stream()
                        .map(development -> IntStream.range(0, development.getAnnotation(CardAmount.class).value())
                .mapToObj(i -> ReflectionUtils.getInstancesOf(development))
                .toList())
                .flatMap(Collection::stream)
                .collect(CollectorsUtils.toShuffledStack());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends DevelopmentCard> boolean canPickDevelopmentCard(@NonNull Class<T> type) {
        return developments.stream().anyMatch(development -> development.getClass() == type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends DevelopmentCard> @NonNull List<Resource> getDevelopmentCardCost(@NonNull Class<T> type) {
        return Arrays.stream(type.getAnnotation(DevelopmentCardCost.class).resources())
                .map(resource -> new ResourceImpl(resource.name()) {}.amount(resource.amount()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends DevelopmentCard> @NonNull DevelopmentCard pickDevelopmentCard(@NonNull Class<T> type) {
        return developments.remove(IndexedStream.from(developments)
                .filter((development, i) -> development.getClass() == type)
                .map((development, i) -> i)
                .findFirst()
                .orElseThrow(RuntimeException::new).intValue());
    }

    /**
     * Creates the special card deck
     *
     * @return the special card deck
     */
    private @NonNull Stack<SpecialCard> createSpecialCardDeck() {
        return ReflectionUtils.getClassesOf(SpecialCard.class, "fr.univnantes.alma.commons.card.special").stream()
                .map(special -> IntStream.range(0, special.getAnnotation(CardAmount.class).value())
                        .mapToObj(i -> ReflectionUtils.getInstancesOf(special))
                        .toList())
                .flatMap(Collection::stream)
                .collect(CollectorsUtils.toShuffledStack());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends SpecialCard> @NonNull Optional<T> pickSpecialCard(@NonNull Class<T> type) {
        return specials.stream()
                .filter(specialCard -> specialCard.getClass() == type)
                .map(specialCard -> (T) specialCard)
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SpecialCard> getSpecialsCards(){
        return specials;
    }

}