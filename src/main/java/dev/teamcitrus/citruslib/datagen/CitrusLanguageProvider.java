package dev.teamcitrus.citruslib.datagen;

import dev.teamcitrus.citruslib.util.JavaUtil;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public abstract class CitrusLanguageProvider extends LanguageProvider {
    private final String modid;

    public CitrusLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
        this.modid = modid;
    }

    public void generateItemLanguageKeys(DeferredRegister<Item> register) {
        generateItemLanguageKeys(register, List.of());
    }

    public void generateItemLanguageKeys(DeferredRegister<Item> register, List<Item> blacklist) {
        var registrar = register.getEntries();
        JavaUtil.takeAll(registrar, i -> i.get() instanceof BlockItem);
        JavaUtil.takeAll(registrar, i -> blacklist.contains(i.get()));
        registrar.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item\\." + this.modid + "\\.", "");
            name = JavaUtil.toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
    }

    public void generateBlockLanguageKeys(DeferredRegister<Block> register) {
        generateBlockLanguageKeys(register, List.of());
    }

    public void generateBlockLanguageKeys(DeferredRegister<Block> register, List<Block> blacklist) {
        var registrar = register.getEntries();
        JavaUtil.takeAll(registrar, i -> i.get() instanceof WallSignBlock);
        JavaUtil.takeAll(registrar, i -> i.get() instanceof WallHangingSignBlock);
        JavaUtil.takeAll(registrar, i -> blacklist.contains(i.get()));
        registrar.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("block\\." + this.modid + "\\.", "");
            name = JavaUtil.toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
    }
}
