package net.junie.herobrinemod;

import net.junie.herobrinemod.entity.client.HerobrineRenderer;
import net.junie.herobrinemod.entity.client.MikuRenderer;
import net.junie.herobrinemod.entity.client.TetoRenderer;
import net.junie.herobrinemod.item.ModItems;
import net.junie.herobrinemod.registry.ModBlocks;
import net.junie.herobrinemod.entity.ModEntities;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.GHASTLY_TOTEM);
            event.accept(ModBlocks.GHASTLY_SKULL);
        }
        //adds herobrine sword to the creative mode tab for combat
        if(event.getTabKey() == CreativeModeTabs.COMBAT)
        {
            event.accept(ModItems.HEROBRINE_SWORD);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            EntityRenderers.register(ModEntities.HEROBRINE.get(), HerobrineRenderer::new);
            EntityRenderers.register(ModEntities.MIKU.get(), MikuRenderer::new);
            EntityRenderers.register(ModEntities.TETO.get(), TetoRenderer::new);
        }
    }
}
