package net.junie.herobrinemod.client.renderer;

import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.entity.custom.HerobrineEntity;
import net.junie.herobrinemod.entity.ModEntities;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HerobrineMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityRenderers {

    public static class HerobrineRenderer extends HumanoidMobRenderer<HerobrineEntity, HumanoidModel<HerobrineEntity>> {
        private static final ResourceLocation TEXTURE =
                new ResourceLocation(HerobrineMod.MODID, "assets/herobrinemod/textures/entity/herobrine.png");

        public HerobrineRenderer(Context context) {
            super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        }

        @Override
        public ResourceLocation getTextureLocation(HerobrineEntity entity) {
            return TEXTURE;
        }
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.HEROBRINE.get(), HerobrineRenderer::new);
    }
}
