package net.junie.herobrinemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.junie.herobrinemod.HerobrineMod;

import net.junie.herobrinemod.entity.custom.MikuEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MikuRenderer extends MobRenderer<MikuEntity, MikuModel<MikuEntity>> {

    public MikuRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MikuModel<>(pContext.bakeLayer(MobModelLayers.MIKU_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MikuEntity pEntity) {
        return new ResourceLocation(HerobrineMod.MODID, "textures/entity/miku.png");
    }

    @Override
    public void render(MikuEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
