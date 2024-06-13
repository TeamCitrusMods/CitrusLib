package dev.teamcitrus.citruslib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.item.ItemStack;

public class ScreenUtils {
    public static boolean isItemInHand(ItemStack stack) {
        return Minecraft.getInstance().screen instanceof AbstractContainerScreen<?> containerScreen && containerScreen.getMenu().getCarried() == stack;
    }
}
