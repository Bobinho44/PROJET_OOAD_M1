package fr.univnantes.alma.core.configuration;

import fr.univnantes.alma.commons.resource.ResourceJSONImpl;
import fr.univnantes.alma.core.resource.ResourceJSON;
import fr.univnantes.alma.commons.utils.yaml.YAML;
import org.springframework.lang.NonNull;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * Interface representing a configuration
 */
public interface Configuration {

    /**
     * Gets the territory amount
     *
     * @param territoryType the territory type
     * @return the territory amount
     */
    static int getTerritoryAmount(@NonNull String territoryType) {
        Objects.requireNonNull(territoryType, "territoryType cannot be null!");

        return YAML.getInt(Paths.get("src/main/resources/config.yml").toString(), territoryType);
    }

    /**
     * Gets the resource amount
     *
     * @param resourceType the resource type
     * @return the resource amount
     */
    static int getResourceAmount(@NonNull String resourceType) {
        Objects.requireNonNull(resourceType, "resourceType cannot be null!");

        return YAML.getInt(Paths.get("src/main/resources/config.yml").toString(), resourceType);
    }

    /**
     * Gets the card amount
     *
     * @param cardType the card type
     * @return the card amount
     */
    static int getCardAmount(@NonNull String cardType) {
        Objects.requireNonNull(cardType, "cardType cannot be null!");

        return YAML.getInt(Paths.get("src/main/resources/config.yml").toString(), cardType);
    }

    /**
     * Gets the development card cost
     *
     * @return the development card cost
     */
    static @NonNull List<ResourceJSON> getDevelopmentCardCost() {
        return YAML.getStringList(Paths.get("src/main/resources/config.yml").toString(), "DevelopmentCardCost").stream()
                .map(resource -> {
                    String[] information = resource.split(":");

                    return (ResourceJSON) new ResourceJSONImpl(information[0], Integer.parseInt(information[1]));
                })
                .toList();
    }

    /**
     * Gets the construction cost
     *
     * @param constructionType the cost type
     * @return the construction cost
     */
    static @NonNull List<ResourceJSON> getConstructionCost(@NonNull String constructionType) {
        Objects.requireNonNull(constructionType, "constructionType cannot be null!");

        return YAML.getStringList(Paths.get("src/main/resources/config.yml").toString(), constructionType).stream()
                .map(resource -> {
                    String[] information = resource.split(":");

                    return (ResourceJSON) new ResourceJSONImpl(information[0], Integer.parseInt(information[1]));
                })
                .toList();
    }

    /**
     * Gets the max dock ratio (for trade with bank)
     *
     * @return the max dock ratio
     */
    static int getMaxDockRatio() {
        return YAML.getInt(Paths.get("src/main/resources/config.yml").toString(), "MaxDockRatio");
    }

}