package svenhjol.charm.base.handler;

import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

import java.util.function.Supplier;

@SuppressWarnings("UnusedReturnValue")
public class RegistryHandler {
    public static Block block(ResourceLocation id, Block block) {
        return Registry.register(Registry.BLOCK, id, block);
    }

    public static <T extends TileEntity> TileEntityType<T> TileEntity(ResourceLocation id, Supplier<T> supplier, Block... blocks) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, id, TileEntityType.Builder.create(supplier, blocks).build(null));
    }

    public static StructureFeature<?, ?> configuredFeature(ResourceLocation id, StructureFeature<?, ?> configuredFeature) {
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, id, configuredFeature);
        return configuredFeature;
    }

    public static <T extends Entity> EntityType<T> entity(ResourceLocation id, EntityType<T> entityType) {
        return Registry.register(Registry.ENTITY_TYPE, id, entityType);
    }

    public static Item item(ResourceLocation id, Item item) {
        return Registry.register(Registry.ITEM, id, item);
    }

    public static LootFunctionType lootFunctionType(ResourceLocation id, LootFunctionType lootFunctionType) {
        return Registry.register(Registry.LOOT_FUNCTION_TYPE, id, lootFunctionType);
    }

    public static PointOfInterestType pointOfInterestType(ResourceLocation id, PointOfInterestType poit) {
        return Registry.register(Registry.POINT_OF_INTEREST_TYPE, id, poit);
    }

    public static <T extends IRecipe<?>> IRecipeType<T> recipeType(String recipeId) {
        return IRecipeType.register(recipeId);
    }

    public static <S extends IRecipeSerializer<T>, T extends IRecipe<?>> S recipeSerializer(String recipeId, S serializer) {
        return IRecipeSerializer.register(recipeId, serializer);
    }

    public static <T extends Container> ContainerType<T> screenHandler(ResourceLocation id, ContainerType.Factory<T> factory) {
        return Registry.register(Registry.MENU, id, new ContainerType<>(factory));
    }

    public static <H extends Container, S extends Screen & ContainerProvider<H>> void screenHandlerClient(ContainerType<H> screenHandler, ScreenRegistry.Factory<H, S> screen) {
        ScreenRegistry.register(screenHandler, screen);
    }

    public static SoundEvent sound(ResourceLocation id, SoundEvent sound) {
        return Registry.register(Registry.SOUND_EVENT, id, sound);
    }

    public static IStructurePieceType structurePiece(ResourceLocation id, IStructurePieceType structurePieceType) {
        return Registry.register(Registry.STRUCTURE_PIECE, id, structurePieceType);
    }

    public static VillagerProfession villagerProfession(ResourceLocation id, VillagerProfession profession) {
        return Registry.register(Registry.VILLAGER_PROFESSION, id, profession);
    }
}
