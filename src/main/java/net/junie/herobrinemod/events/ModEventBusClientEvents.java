package net.junie.herobrinemod.events;

import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.entity.client.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HerobrineMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MobModelLayers.HEROBRINE_LAYER, HerobrineModel::createBodyLayer);
        event.registerLayerDefinition(MobModelLayers.MIKU_LAYER, MikuModel::createBodyLayer);
        event.registerLayerDefinition(MobModelLayers.TETO_LAYER, TetoModel::createBodyLayer);
        event.registerLayerDefinition(MobModelLayers.GHASTLY_SKULL_LAYER, GhastlySkullModel::createBodyLayer);
    }

}
