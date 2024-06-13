package dev.teamcitrus.citruslib.util;

import net.minecraft.DetectedVersion;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class DatagenUtils {
    public static PackMetadataGenerator makeMetadataFile(PackOutput packOutput, String modID) {
        return new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal("Resources for " + StringUtils.capitalize(modID)),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE))
        ));
    }
}
