package fr.univnantes.alma.commons.utils.stream;

import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Utility class that handles indexed stream
 */
public interface IndexedStream<K> {

    /**
     * Creates the indexed stream from the collection
     *
     * @param collection the collection
     * @return the indexed stream
     */
    static @Nonnull <K> IndexedStream<K> from(@Nonnull Collection<K> collection) {
        Objects.requireNonNull(collection, "collection cannot be null!");

        Map<K, Integer> map = new HashMap<>();
        int i = 0;

        for (K element : collection) {
            map.put(element, i++);
        }

        return from(map);
    }

    /**
     * Creates the indexed stream from a map
     *
     * @param map the map
     * @return the indexed stream
     */
    static @Nonnull <K> IndexedStream<K> from(@Nonnull Map<K, Integer> map) {
        Objects.requireNonNull(map, "map cannot be null!");

        return from(map.entrySet().stream());
    }

    /**
     * Creates the indexed stream from a stream
     *
     * @param stream the stream
     * @return the indexed stream
     */
    static @Nonnull <K> IndexedStream<K> from(@Nonnull Stream<Map.Entry<K, Integer>> stream) {
        Objects.requireNonNull(stream, "stream cannot be null!");

        return () -> stream;
    }

    /**
     * Gets an indexed stream consisting of the elements of this stream that match the given predicate
     *
     * @param mapper the mapper
     * @return the new IndexedStream
     */
    default @Nonnull IndexedStream<K> filter(@Nonnull BiPredicate<? super K, Integer> mapper) {
        Objects.requireNonNull(mapper, "mapper cannot be null!");

        return from(entries().filter(e -> mapper.test(e.getKey(), e.getValue())));
    }

    /**
     * Gets an indexed stream consisting of the results of applying the given function to the elements of this stream
     *
     * @param mapper the mapper
     * @param <R>    the type of the result
     * @return the new indexed stream
     */
    default @Nonnull <R> Stream<R> map(@Nonnull BiFunction<? super K, Integer, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "mapper cannot be null!");

        return entries().map(e -> mapper.apply(e.getKey(), e.getValue()));
    }

    /**
     * Gets a stream of all entries of the indexed stream
     *
     * @return the stream with all entries of the indexed stream
     */
    @NonNull Stream<Map.Entry<K, Integer>> entries();

    /**
     * Performs an action for each element of this stream
     *
     * @param action the action
     */
    default void forEach(@Nonnull BiConsumer<? super K, Integer> action) {
        Objects.requireNonNull(action, "action cannot be null!");

        entries().forEach(e -> action.accept(e.getKey(), e.getValue()));
    }

}