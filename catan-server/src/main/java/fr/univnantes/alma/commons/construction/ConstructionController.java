package fr.univnantes.alma.commons.construction;

import fr.univnantes.alma.commons.construction.constructableArea.ConstructableAreaJSON;
import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.core.configuration.Configuration;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.ConstructionManager;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.territory.Territory;
import org.springframework.lang.NonNull;

import java.util.*;

public class ConstructionController implements ConstructionManager {

    /**
     * Fields
     */
    private final Map<UUID, ConstructableArea<? extends Construction>> constructableAreas = new HashMap<>();

    /**
     * Creates a new construction manager
     */
    public ConstructionController(@NonNull List<Territory> territories) {
        //TODO createConstructableArea
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<ConstructableAreaJSON> getConstructableAreasInformation() {
        return constructableAreas.values().stream()
                .map(constructableArea -> new ConstructableAreaJSON(constructableArea.getUUID(), constructableArea.getClass().getName()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> ConstructableArea<T> getConstructableArea(@NonNull ConstructableAreaJSON constructableAreaJSON, @NonNull Class<T> type) throws RuntimeException {
        try {

            if (!Class.forName(constructableAreaJSON.getType()).equals(type)) {
                throw new RuntimeException();
            }

            return (ConstructableArea<T>) Optional.ofNullable(constructableAreas.get(constructableAreaJSON.getUUID())).orElseThrow();

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean hasConstructableArea(@NonNull ConstructableAreaJSON constructableAreaJSON, @NonNull Class<T> type) {
        try {
            getConstructableArea(constructableAreaJSON, type);
            System.out.println(Class.forName(constructableAreaJSON.getType()).getName());
            return Class.forName(constructableAreaJSON.getType()).equals(type);
        }

        catch (Exception e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull <T extends Construction> T generateConstruction(@NonNull ConstructionJSON constructionJSON, @NonNull Class<T> type, @NonNull Player player) {
        try {
            return (T) ReflectionUtils.getInstancesOf(Class.forName(constructionJSON.getType()), player);
        }

        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean hasConstruction(@NonNull ConstructionJSON constructionJSON, @NonNull Class<T> type, @NonNull Player player) {
        try {
            generateConstruction(constructionJSON, type, player);
            return true;
        }

        catch (Exception e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Resource> getConstructionCost(@NonNull Construction construction) {
        return Configuration.getConstructionCost(construction.getClass().getSimpleName()).stream()
                .map(resource -> (Resource) new ResourceImpl(resource.getName()) {}.amount(resource.getAmount()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean isConstructable(@NonNull ConstructableArea<T> constructableArea, @NonNull T construction) {
        return constructableArea.isConstructable(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction>  void construct(@NonNull ConstructableArea<T> constructableArea, @NonNull T construction) {
        constructableArea.construct(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerDockRatio(@NonNull Player player, @NonNull Resource resource) {
        return constructableAreas.values().stream()
                .filter(ConstructableArea::hasConstruction)
                .filter(constructableArea -> constructableArea.getConstruction().get().getOwner().equals(player))
                .filter(ConstructableArea::hasDock)
                .map(constructableArea -> constructableArea.getDock().get().getResource().equals(resource) ? 2 :3)
                .min(Integer::compare)
                .orElse(4);
    }

    /**private <T extends Construction> @NonNull Optional<ConstructableArea<T>> getConstructableArea(@NonNull UUID uuid, @NonNull Class<T> type) {
        return constructableAreas.entrySet().stream()
                .filter(constructableArea -> constructableArea.getKey().equals(uuid))
                .filter(constructableArea -> GenericTypeResolver.resolveTypeArgument(constructableArea.getValue().getConstructStrategy().getClass(), ConstructStrategy.class).isAssignableFrom(type))
                .map(constructableArea -> (ConstructableArea<T>) constructableArea.getValue())
                .findFirst();
    }

    public <T extends Construction> boolean hasConstructableArea(@NonNull UUID uuid, @NonNull Class<T> type) {
        return getConstructableArea(uuid, type).isPresent();
    }

    public <T extends Construction> T generateConstruction(@NonNull Player owner, @NonNull Class<T> type) {
        return ReflectionUtils.getInstancesOf(type);
    }

    public int getTradeCost(@NonNull Player player, @NonNull Resource resource) {
        return constructableAreas.values().stream()
                .filter(ConstructableArea::hasConstruction)
                .filter(constructableArea -> constructableArea.getConstruction().get().getOwner().equals(player))
                .filter(ConstructableArea::hasDock)
                .map(constructableArea -> constructableArea.getDock().get().getResource().equals(resource) ? 2 :3)
                .min(Integer::compare)
                .orElse(4);
    }

    public <T extends Construction> @NonNull List<Resource> getConstructionCost(@NonNull Class<T> type) {
        return Arrays.stream(type.getAnnotation(ConstructionCost.class).resources())
                .map(resource -> new ResourceImpl(resource.name()) {}.amount(resource.amount()))
                .toList();
    }

    public <T extends Construction> boolean isConstructable(@NonNull UUID uuid, @NonNull Class<T> type, @NonNull T construction) {
        return getConstructableArea(uuid, type)
                .map(area -> area.isConstructable(construction))
                .orElse(false);
    }

    public <T extends Construction>  void construct(@NonNull UUID uuid, @NonNull Class<T> type, @NonNull T construction) {
        getConstructableArea(uuid, type).ifPresent(area -> area.construct(construction));
    }*/

}