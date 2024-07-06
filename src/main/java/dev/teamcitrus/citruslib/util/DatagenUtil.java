package dev.teamcitrus.citruslib.util;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.teamcitrus.citruslib.codec.CodecProvider;
import net.minecraft.DetectedVersion;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class DatagenUtil {
    public static PackMetadataGenerator makeMetadataFile(PackOutput packOutput, String modID) {
        return new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal("Resources for " + StringUtils.capitalize(modID)),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE))
        ));
    }

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
