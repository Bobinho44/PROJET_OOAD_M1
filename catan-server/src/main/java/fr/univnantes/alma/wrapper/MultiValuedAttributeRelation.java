package fr.univnantes.alma.wrapper;

import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.Stream;

/**
 * Wrapper of multivalued attribute relation
 */
public class MultiValuedAttributeRelation<K, V> {

    /**
     * Fields
     */
    private final Map<K, V> map;

    /**
     * Creates a new multivalued attribute relation
     */
    public MultiValuedAttributeRelation(@NonNull Map<K, V> map) {
        this.map = map;
    }

    /**
     * Creates a new empty multivalued attribute relation
     */
    public MultiValuedAttributeRelation() {
        map = new HashMap<>();
    }

    /**
     * Creates a new not empty multivalued attribute relation
     *
     * @param key   the initial wrapper key
     * @param value the initial wrapper value
     */
    public MultiValuedAttributeRelation(@NonNull K key, @NonNull V value) {
        Objects.requireNonNull(key, "key cannot be null!");
        Objects.requireNonNull(value, "value cannot be null!");

        map = Map.of(key, value);
    }

    /**
     * Puts a new relation to the multivalued attribute relation
     *
     * @param key   the key
     * @param value the value
     */
    public void put(@NonNull K key, @NonNull V value) {
        Objects.requireNonNull(key, "key cannot be null!");
        Objects.requireNonNull(value, "value cannot be null!");

        map.put(key, value);
    }

    /**
     * Removes a relation from the multivalued attribute relation
     *
     * @param key the key
     */
    public void remove(@NonNull K key) {
        Objects.requireNonNull(key, "key cannot be null!");

        map.remove(key);
    }

    /**
     * Gets a relation from the multivalued attribute relation
     *
     * @param key the key of the relation
     */
    public @NonNull Optional<V> get(@NonNull K key) {
        Objects.requireNonNull(key, "key cannot be null!");

        return Optional.ofNullable(map.get(key));
    }

    /**
     * Gets a set containing all relations from the multivalued attribute relation
     *
     * @return the set containing all relations from the multivalued attribute relation
     */
    public @NonNull Set<K> keySet() {
        return map.keySet();
    }

    /**
     * Gets a stream of all relations from the multivalued attribute relation
     *
     * @return the stream of all relations from the multivalued attribute relation
     */
    public @NonNull Stream<Map.Entry<K, V>> stream() {
        return map.entrySet().stream();
    }

}
