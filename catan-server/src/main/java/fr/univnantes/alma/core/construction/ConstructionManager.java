package fr.univnantes.alma.core.construction;

import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConstructionManager {

    <T extends Construction> Optional<ConstructableArea<T>> getConstructableArea(@NonNull UUID uuid, @NonNull Class<T> type);


    <T extends Construction> List<Resource> getConstructionCost(@NonNull Class<T> type);


    <A extends Construction, T extends A> boolean isConstructable(@NonNull ConstructableArea<A> area, @NonNull T construction);

    <A extends Construction, T extends A> void construct(@NonNull ConstructableArea<A> area, @NonNull T construction);

}