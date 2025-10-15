package net.junie.herobrinemod;

import net.junie.herobrinemod.item.ModItems;
import net.junie.herobrinemod.registry.ModBlocks;
import net.junie.herobrinemod.registry.ModEntities;
import net.junie.herobrinemod.events.ModEvents;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(HerobrineMod.MODID)
public class HerobrineMod {

    public static final String MODID = "herobrinemod";

    public HerobrineMod() {

        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(ModEvents.class);
    }
    //NOT WORKING
    private void addCreative(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
            event.accept(ModBlocks.GHASTLY_TOTEM);
        }
    }
}
