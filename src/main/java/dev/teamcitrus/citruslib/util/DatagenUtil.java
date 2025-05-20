package dev.teamcitrus.citruslib.util;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.teamcitrus.citruslib.codec.CodecProvider;

public class DatagenUtil {
    /**
     * Converts an object to json via codec.
     *
     * @throws IllegalStateException if the serialization fails
     */
    public static <T> JsonElement toJson(T object, Codec<T> codec) {
        return codec.encodeStart(JsonOps.INSTANCE, object).getOrThrow();
    }

    /**
     * Converts a {@link CodecProvider} object to json via its provided codec.
     */
    @SuppressWarnings("unchecked")
    public static <T extends CodecProvider<T>> JsonElement toJson(T object) {
        return toJson(object, (Codec<T>) object.getCodec());
    }
}
