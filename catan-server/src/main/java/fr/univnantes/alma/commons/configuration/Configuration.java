package fr.univnantes.alma.commons.configuration;

import fr.univnantes.alma.commons.resource.ResourceJSON;
import fr.univnantes.alma.commons.utils.yaml.YAML;
import fr.univnantes.alma.core.resource.IResourceJSON;
import org.springframework.lang.NonNull;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * Interface representing a configuration
 */
public abstract class Configuration {

    /**
     * Gets the territory amount
     *
     * @param territoryType the territory type
     * @return the territory amount
     */
    public static int getTerritoryAmount(@NonNull String territoryType) {
        Objects.requireNonNull(territoryType, "territoryType cannot be null!");

        return YAML.getInt(Paths.get("src/main/resources/config.yml").toString(), territoryType);
    }

    /**
     * Gets the resource amount
     *
     * @param resourceType the resource type
     * @return the resource amount
     */
    public static int getResourceAmount(@NonNull String resourceType) {
        Objects.requireNonNull(resourceType, "resourceType cannot be null!");

        return YAML.getInt(Paths.get("src/main/resources/config.yml").toString(), resourceType);
    }

    /**
     * Gets the card amount
     *
     * @param cardType the card type
     * @return the card amount
     */
    public static int getCardAmount(@NonNull String cardType) {
        Objects.requireNonNull(cardType, "cardType cannot be null!");

        return YAML.getInt(Paths.get("src/main/resources/config.yml").toString(), cardType);
    }

    /**
     * Gets the development card cost
     *
     * @return the development card cost
     */
    public static @NonNull List<IResourceJSON> getDevelopmentCardCost() {
        return YAML.getStringList(Paths.get("src/main/resources/config.yml").toString(), "DevelopmentCardCost").stream()
                .map(resource -> {
                    String[] information = resource.split(":");

                    return (IResourceJSON) new ResourceJSON(information[0], Integer.parseInt(information[1]));
                })
                .toList();
    }

    /**
     * Gets the construction cost
     *
     * @param constructionType the cost type
     * @return the construction cost
     */
    public static @NonNull List<IResourceJSON> getConstructionCost(@NonNull String constructionType) {
        Objects.requireNonNull(constructionType, "constructionType cannot be null!");

        return YAML.getStringList(Paths.get("src/main/resources/config.yml").toString(), constructionType).stream()
                .map(resource -> {
                    String[] information = resource.split(":");

                    return (IResourceJSON) new ResourceJSON(information[0], Integer.parseInt(information[1]));
                })
                .toList();
    }

    /**
     * Gets the max dock ratio (for trade with bank)
     *
     * @return the max dock ratio
     */
    public static int getMaxDockRatio() {
        return YAML.getInt(Paths.get("src/main/resources/config.yml").toString(), "MaxDockRatio");
    }

}