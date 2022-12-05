package fr.univnantes.alma.commons.utils.collector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
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

}
