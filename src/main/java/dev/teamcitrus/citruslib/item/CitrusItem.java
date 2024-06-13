package dev.teamcitrus.citruslib.item;

import net.minecraft.world.item.Item;

public class CitrusItem extends Item {
    public CitrusItem(Properties pProperties) {
        super(pProperties);
    }

    public CitrusItem() {
        this(new Properties());
    }

    public boolean isWIP() {
        return false;
    }

    public Properties defaultProperties() {
        return new Properties();
    }
}
