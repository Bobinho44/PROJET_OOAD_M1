package fr.univnantes.alma.commons.utils.collector;

import org.springframework.lang.NonNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectorsUtils {

    public static <T> Collector<T, ?, List<T>> toShuffledList() {
        return Collectors.collectingAndThen(
                Collectors.toCollection(ArrayList::new),
                list -> {
                    Collections.shuffle(list);
                    return list;
                });
    }

    public static <T> Collector<T, ?, Stack<T>> toShuffledStack() {
        return Collectors.collectingAndThen(
                Collectors.toCollection(Stack::new),
                list -> {
                    Collections.shuffle(list);
                    return list;
                });
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toShuffledMap(@NonNull Function<T, K> keyMapper, Function<T, U> valueMapper) {
        return Collectors.collectingAndThen(
                Collectors.toCollection(ArrayList::new),
                list -> {
                    List<T> objects = new ArrayList<>(list);
                    Collections.shuffle(objects);
                    return objects.stream().collect(Collectors.toMap(keyMapper, valueMapper));
                });
    }

}
