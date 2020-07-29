package svenhjol.charm.module;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import svenhjol.meson.MesonModule;
import svenhjol.meson.handler.OverrideHandler;
import svenhjol.meson.iface.Config;
import svenhjol.meson.iface.Module;
import svenhjol.meson.mixin.DispenserBlockAccessor;

public class StackablePotions extends MesonModule {
    @Config(name = "Stack size", description = "Maximum potion stack size.")
    public static int stackSize = 16;

    @Module(description = "Allows potions to stack.")
    public StackablePotions() {}

    @Override
    public void init() {
        if (enabled) {
            PotionItem potionItem = new PotionItem((new Item.Properties()).maxStackSize(stackSize).group(ItemGroup.BREWING));
            SplashPotionItem splashPotionItem = new SplashPotionItem((new Item.Properties()).maxStackSize(stackSize).group(ItemGroup.BREWING));
            LingeringPotionItem lingeringPotionItem = new LingeringPotionItem((new Item.Properties()).maxStackSize(stackSize).group(ItemGroup.BREWING));

            // re-register dispenser splash behavior
            IDispenseItemBehavior splashBehavior = DispenserBlockAccessor.getDispenseBehaviorRegistry().get(Items.SPLASH_POTION);
            DispenserBlock.registerDispenseBehavior(splashPotionItem, splashBehavior);

            // override vanilla potion items
            OverrideHandler.changeVanillaItem(mod, potionItem, new ResourceLocation("potion"));
            OverrideHandler.changeVanillaItem(mod, splashPotionItem, new ResourceLocation("splash_potion"));
            OverrideHandler.changeVanillaItem(mod, lingeringPotionItem, new ResourceLocation("lingering_potion"));
        }
    }

    public static boolean isValidItemStack(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.POTION
            || item == Items.SPLASH_POTION
            || item == Items.LINGERING_POTION;
    }
}
