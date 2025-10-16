package net.junie.herobrinemod.client.renderer;

import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.block.GhastlySkullBlock;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HerobrineMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSkullRenderers {

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {

        SkullBlockRenderer.SKIN_BY_TYPE.put(
                GhastlySkullBlock.GHASTLY_TYPE,
                new ResourceLocation(HerobrineMod.MODID, "textures/block/ghastly_skull.png")
        );


    }
}
