package dev.teamcitrus.citruslib.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class JavaUtil {
    public static String toTitleCase(String givenString, String regex) {
        String[] stringArray = givenString.split(regex);
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : stringArray) {
            stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(regex);
        }
        return stringBuilder.toString().trim().replaceAll(regex, " ").substring(0, stringBuilder.length() - 1);
    }

    public static String join(char character, Object... vars) {
        return StringUtils.join(Lists.newArrayList(vars).stream().map(Object::toString).collect(Collectors.toList()), character);
    }

    public static <T> void takeAll(Collection<T> src, Predicate<T> pred) {
        src.removeIf(pred);
    }

    public static <T> Supplier<T> supplier(T object) {
        return () -> object;
    }
}
