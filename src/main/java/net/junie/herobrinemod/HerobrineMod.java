package net.junie.herobrinemod;

import net.junie.herobrinemod.item.ModItems;
import net.junie.herobrinemod.registry.ModBlocks;
import net.junie.herobrinemod.registry.ModEntities;
import net.junie.herobrinemod.events.ModEvents;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(HerobrineMod.MODID)
public class HerobrineMod {

    public static final String MODID = "herobrinemod";

    public HerobrineMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);

        ModItems.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
            event.accept(ModBlocks.GHASTLY_TOTEM);
        }
    }
}
