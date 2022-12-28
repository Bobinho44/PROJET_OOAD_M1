package fr.univnantes.alma.commons.utils.collector;

import org.springframework.lang.NonNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Utility class that handles collectors
 */
public final class CollectorsUtils {

    /**
     * Disabling the constructor (utility class)
     */
    private CollectorsUtils() {}

    /**
     * Creates the collector to collect element to shuffled map
     *
     * @param keyMapper the key mapper
     * @param valueMapper the value mapper
     * @return the collector to collect element to shuffled map
     */
    public static @NonNull <T, K, U> Collector<T, ?, Map<K, U>> toShuffledMap(@NonNull Function<T, K> keyMapper, @NonNull Function<T, U> valueMapper) {
        Objects.requireNonNull(keyMapper, "keyMapper cannot be null!");
        Objects.requireNonNull(valueMapper, "valueMapper cannot be null!");

        return Collectors.collectingAndThen(
                Collectors.toCollection(ArrayList::new),

                //Shuffle the list before collecting
                list -> {
                    List<T> objects = new ArrayList<>(list);
                    Collections.shuffle(objects);
                    return objects.stream().collect(Collectors.toMap(keyMapper, valueMapper));
                });
    }

}
