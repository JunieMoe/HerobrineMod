package net.junie.herobrinemod.registry;

import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.block.GhastlyTotemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, HerobrineMod.MODID);

    public static final RegistryObject<Block> GHASTLY_TOTEM = BLOCKS.register("ghastly_totem",
            () -> new GhastlyTotemBlock(BlockBehaviour.Properties
                    .of() // No Material
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(3.0F, 9.0F)
                    .sound(SoundType.STONE)
                    .lightLevel(state -> 6)
            ));
}
