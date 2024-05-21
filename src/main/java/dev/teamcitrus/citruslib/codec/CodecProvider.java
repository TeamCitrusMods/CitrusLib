package dev.teamcitrus.citruslib.codec;

import com.mojang.serialization.Codec;

/**
 * Taken from Placebo with consent of Shadows
 * @author Shadows of Fire
 * @link <a href="https://github.com/Shadows-of-Fire/Placebo/tree/1.20.4">...</a>
 */
public interface CodecProvider<R> {
    Codec<? extends R> getCodec();
}
