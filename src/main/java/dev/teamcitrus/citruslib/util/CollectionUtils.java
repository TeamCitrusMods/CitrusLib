package dev.teamcitrus.citruslib.util;

import java.util.Collection;
import java.util.function.Predicate;

public class CollectionUtils {
    public static <T> void takeAll(Collection<T> src, Predicate<T> pred) {
        src.removeIf(pred);
    }
}
