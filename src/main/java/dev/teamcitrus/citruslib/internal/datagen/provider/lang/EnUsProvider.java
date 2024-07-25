package dev.teamcitrus.citruslib.internal.datagen.provider.lang;

import dev.teamcitrus.citruslib.CitrusLib;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnUsProvider extends LanguageProvider {
    public EnUsProvider(PackOutput output) {
        super(output, CitrusLib.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("tooltip.citruslib.wip", "This Item is still WIP");
    }
}
