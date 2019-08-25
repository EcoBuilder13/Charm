package svenhjol.meson.handler;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import svenhjol.meson.iface.IMesonBlock;
import svenhjol.meson.iface.IMesonPotion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@Mod.EventBusSubscriber(bus = MOD)
public class RegistryHandler
{
    public static Map<Class<?>, List<IForgeRegistryEntry<?>>> objects = new HashMap();

    public static void registerBlock(Block block, ResourceLocation res)
    {
        addRegisterable(block, res);

        // also register the block item
        if (block instanceof IMesonBlock) {
            addRegisterable(((IMesonBlock)block).getBlockItem(), res);
        }
    }

    public static void registerContainer(ContainerType<?> container, ResourceLocation res)
    {
        addRegisterable(container, res);
    }

    public static void registerEffect(Effect effect, ResourceLocation res)
    {
        addRegisterable(effect, res);
    }

    public static void registerEnchantment(Enchantment enchantment, ResourceLocation res)
    {
        addRegisterable(enchantment, res);
    }

    public static void registerEntity(EntityType<?> entity, ResourceLocation res)
    {
        addRegisterable(entity, res);
    }

    public static void registerItem(Item item, ResourceLocation res)
    {
        addRegisterable(item, res);
    }

    public static void registerPotion(Potion potion, ResourceLocation res)
    {
        addRegisterable(potion, res);

        if (potion instanceof IMesonPotion) {
            ((IMesonPotion)potion).registerRecipe();
        }
    }

    public static void registerSound(SoundEvent sound)
    {
        addRegisterable(sound, sound.getRegistryName());
    }

    public static void registerTile(TileEntityType<?> tile, ResourceLocation res)
    {
        addRegisterable(tile, res);
    }

    @SubscribeEvent
    public static void onRegister(final Register event)
    {
        IForgeRegistry registry = event.getRegistry();
        Class<?> type = registry.getRegistrySuperType();

        if (objects.containsKey(type)) {
            objects.get(type).forEach(registry::register);
            objects.remove(type);
        }
    }

    private static void addRegisterable(IForgeRegistryEntry<?> obj, ResourceLocation res)
    {
        Class<?> type = obj.getRegistryType();
        if (!objects.containsKey(type)) objects.put(type, new ArrayList<>());

        if (!(objects.containsKey(type) && objects.get(type).contains(obj))) {
            if (res == null && obj.getRegistryName() == null) {
                return; // can't set it
            }

            if (obj.getRegistryName() == null) {
                obj.setRegistryName(res);
            }
            objects.get(type).add(obj);
        }
    }
}
