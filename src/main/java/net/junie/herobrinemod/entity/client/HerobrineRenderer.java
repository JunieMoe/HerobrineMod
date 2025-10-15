package net.junie.herobrinemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.entity.custom.HerobrineEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HerobrineRenderer extends HumanoidMobRenderer<HerobrineEntity, HerobrineModel<HerobrineEntity>> {

    public HerobrineRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new HerobrineModel<>(pContext.bakeLayer(MobModelLayers.HEROBRINE_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(HerobrineEntity pEntity) {
        return new ResourceLocation(HerobrineMod.MODID, "textures/entity/herobrine.png");
    }

    @Override
    public void render(HerobrineEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
