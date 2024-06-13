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
        add("tooltip.citruslib.wip", "Item is W.I.P");
        add("command.citruslib.team.create.must_leave", "YOU ARE ALREADY IN A TEAM!\\nYou must leave to create a new one!\\n%s");
        add("command.citruslib.team.leave.tooltip", "Click to leave team");
    }
}
