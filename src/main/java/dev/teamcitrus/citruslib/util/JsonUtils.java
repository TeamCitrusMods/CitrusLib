package dev.teamcitrus.citruslib.util;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.apache.logging.log4j.Logger;

/**
 * Taken from Placebo with consent of Shadows
 * @author Shadows of Fire
 * @link <a href="https://github.com/Shadows-of-Fire/Placebo/tree/1.20.4">...</a>
 */
public class JsonUtils {
    public static boolean checkAndLogEmpty(JsonElement e, ResourceLocation id, String type, Logger logger) {
        String s = e.toString();
        if (s.isEmpty() || "{}".equals(s)) {
            logger.error("Ignoring {} item with id {} as it is empty.  Please switch to a condition-false json instead of an empty one.", type, id);
            return false;
        }
        return true;
    }

    public static boolean checkConditions(JsonElement e, ResourceLocation id, String type, Logger logger, ConditionalOps<JsonElement> ops) {
        if (ICondition.conditionsMatched(ops, e.getAsJsonObject())) {
            return true;
        }
        logger.trace("Skipping loading {} item with id {} as it's conditions were not met", type, id);
        return false;
    }
}
