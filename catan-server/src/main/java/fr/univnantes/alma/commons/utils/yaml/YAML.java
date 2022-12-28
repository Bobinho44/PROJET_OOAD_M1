package fr.univnantes.alma.commons.utils.yaml;

import fr.univnantes.alma.core.exception.ConfigFileNotFoundException;
import fr.univnantes.alma.core.exception.ConfigValueNotFoundException;
import org.springframework.lang.NonNull;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Utility class that handles YAML files
 */
public final class YAML {

    /**
     * Disabling the constructor (utility class)
     */
    private YAML() {}

    /**
     * Fields
     */
    private static final Map<String, Map<String, Object>> yamls = new HashMap<>();

    /**
     * Gets the data from the configuration file
     *
     * @param configFilePath the configuration file path
     * @return the data from the configuration file
     * @throws ConfigFileNotFoundException if the configuration file path is not valid
     */
    private static Map<String, Object> getData(@NonNull String configFilePath) throws ConfigFileNotFoundException {
        Objects.requireNonNull(configFilePath, "configFilePath cannot be null!");

        return Optional.ofNullable(yamls.get(configFilePath)).orElseGet(() -> {

            //Retrieves data if it has not yet been loaded
            try {
                Yaml yaml = new Yaml();
                FileInputStream inputStream = new FileInputStream(configFilePath);
                Map<String, Object> loadedData = yaml.load(inputStream);
                yamls.put(configFilePath, loadedData);
                inputStream.close();

                return loadedData;
            }

            //The data could not be loaded
            catch (IOException e) {
                throw new ConfigFileNotFoundException();
            }
        });
    }

    /**
     * Gets the int from the configuration file
     *
     * @param configFilePath the configuration file path
     * @param objectPath the int path
     * @return the int from the configuration file
     * @throws ConfigValueNotFoundException if the object was not found in the configuration
     */
    public static int getInt(@NonNull String configFilePath, @NonNull String objectPath) throws ConfigValueNotFoundException {
        Objects.requireNonNull(configFilePath, "configFilePath cannot be null!");
        Objects.requireNonNull(objectPath, "objectPath cannot be null!");

        //Gets the int from the data
        try {
            return (int) getData(configFilePath).get(objectPath);
        }

        //The configuration file path or the object path are not value
        catch (ConfigFileNotFoundException | ClassCastException e) {
            throw new ConfigValueNotFoundException();
        }

    }

    /**
     * Gets the string list from the configuration file
     *
     * @param configFilePath the configuration file path
     * @param objectPath the string list path
     * @return the string list from the configuration file
     * @throws ConfigValueNotFoundException if the object was not found in the configuration
     */
    @SuppressWarnings("unchecked")
    public static List<String> getStringList(@NonNull String configFilePath, @NonNull String objectPath) throws ConfigValueNotFoundException {
        Objects.requireNonNull(configFilePath, "configFilePath cannot be null!");
        Objects.requireNonNull(objectPath, "objectPath cannot be null!");

        //Gets the string list from the data
        try {
            return ((List<String>) getData(configFilePath).get(objectPath));
        }

        //The configuration file path or the object path are not value
        catch (ConfigFileNotFoundException | ClassCastException e) {
            throw new ConfigValueNotFoundException();
        }
    }

}