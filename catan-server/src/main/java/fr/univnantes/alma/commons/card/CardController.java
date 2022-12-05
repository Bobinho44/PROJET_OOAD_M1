package fr.univnantes.alma.commons.card;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.utils.collector.CollectorsUtils;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.core.card.CardManager;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.IntStream;

public class CardController implements CardManager {

    /**
     * Fields
     */
    private final Stack<DevelopmentCard> developments;
    private final Stack<SpecialCard> specials;

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
    public boolean canPickDevelopmentCard() {
        return !developments.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Resource> getDevelopmentCardCost() {
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull DevelopmentCard pickDevelopmentCard() {
        return developments.pop();
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
    public <S extends SpecialCard> @NonNull Optional<S> pickSpecialCard(@NonNull Class<S> type) {
        return specials.stream()
                .filter(specialCard -> specialCard.getClass() == type)
                .map(specialCard -> (S) specialCard)
                .findFirst();
    }

}