package fr.univnantes.alma.core.configuration;

import fr.univnantes.alma.commons.resource.ResourceJSON;
import fr.univnantes.alma.commons.utils.yaml.YAML;
import org.springframework.lang.NonNull;

import java.nio.file.Paths;
import java.util.List;

public final class Configuration {

    private Configuration() {}

    private static final String ABSOLUTE_PATH = Paths.get("src/main/resources/config.yml").toString();

    public static int getTerritoryAmount(@NonNull String territoryType) {
        return YAML.getInt(ABSOLUTE_PATH, territoryType);
    }

    public static int getResourceAmount(@NonNull String resourceType) {
        return YAML.getInt(ABSOLUTE_PATH, resourceType);
    }

    public static int getCardAmount(@NonNull String cardType) {
        return YAML.getInt(ABSOLUTE_PATH, cardType);
    }

    public static List<ResourceJSON> getDevelopmentCardCost() {
        return YAML.getStringList(ABSOLUTE_PATH, "DevelopmentCardCost").stream()
                .map(resource -> {
                    String[] information = resource.split(":");

                    return new ResourceJSON(information[0], Integer.parseInt(information[1]));
                })
                .toList();
    }

    public static List<ResourceJSON> getConstructionCost(@NonNull String constructionType) {
        return YAML.getStringList(ABSOLUTE_PATH, constructionType).stream()
                .map(resource -> {
                    String[] information = resource.split(":");

                    return new ResourceJSON(information[0], Integer.parseInt(information[1]));
                })
                .toList();
    }
}
