package net.junie.herobrinemod.registry;

import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.block.GhastlyTotemBlock;
import net.junie.herobrinemod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, HerobrineMod.MODID);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static final RegistryObject<Block> GHASTLY_TOTEM = BLOCKS.register("ghastly_totem",
            () -> new GhastlyTotemBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(3.0F, 9.0F)
                    .sound(SoundType.STONE)
                    .lightLevel(state -> 6)
            ));
}
