package net.junie.herobrinemod;

import net.junie.herobrinemod.registry.ModBlocks;
import net.junie.herobrinemod.registry.ModEntities;
import net.junie.herobrinemod.events.ModEvents;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Main mod class for HerobrineMod
 */
@Mod(HerobrineMod.MODID)
public class HerobrineMod {

    public static final String MODID = "herobrinemod";

    public HerobrineMod() {
        // Register mod blocks
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register mod entities
        ModEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register event handlers
        MinecraftForge.EVENT_BUS.register(ModEvents.class);
    }
}
