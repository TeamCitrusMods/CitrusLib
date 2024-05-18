package dev.teamcitrus.citruslib.util;

import net.minecraft.DetectedVersion;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public class DatagenUtils {
    /**
     * A method grabbed from Lodestone Lib by Egshels and SammySemiColon to capitalise names and replace [regex] with a space
     * <a href="https://github.com/LodestarMC/Lodestone/blob/65c087683cc1ac2698c4365d1026b00979234443/src/main/java/team/lodestar/lodestone/helpers/DataHelper.java#L36C30-L36C30">...</a>
     */
    private String toTitleCase(String givenString, String regex) {
        String[] stringArray = givenString.split(regex);
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : stringArray) {
            stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(regex);
        }
        return stringBuilder.toString().trim().replaceAll(regex, " ").substring(0, stringBuilder.length() - 1);
    }

    /**
     * removes all entry from a collection based off of a predicate and returns all items removed in a new collection
     * A smaller version of this: <a href="https://github.com/LodestarMC/Lodestone/blob/65c087683cc1ac2698c4365d1026b00979234443/src/main/java/team/lodestar/lodestone/helpers/DataHelper.java#L103">...</a>
     */
    public static <T> void takeAll(Collection<T> src, Predicate<T> pred) {
        src.removeIf(pred);
    }

    public static PackMetadataGenerator makeMetadataFile(PackOutput packOutput, String modID) {
        return new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal("Resources for " + modID),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE))
        ));
    }
}
