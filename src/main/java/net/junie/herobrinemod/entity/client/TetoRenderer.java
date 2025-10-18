package net.junie.herobrinemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.entity.custom.TetoEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TetoRenderer extends MobRenderer<TetoEntity, TetoModel<TetoEntity>> {

    public TetoRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TetoModel<>(pContext.bakeLayer(MobModelLayers.TETO_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(TetoEntity pEntity) {
        return new ResourceLocation(HerobrineMod.MODID, "textures/entity/teto.png");
    }

    @Override
    public void render(TetoEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
