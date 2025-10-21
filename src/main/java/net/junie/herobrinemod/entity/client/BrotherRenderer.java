package net.junie.herobrinemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.entity.custom.BrotherEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BrotherRenderer extends MobRenderer<BrotherEntity, BrotherModel<BrotherEntity>> {

    public BrotherRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BrotherModel<>(pContext.bakeLayer(MobModelLayers.BROTHER_LAYER)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(BrotherEntity pEntity) {
        return new ResourceLocation(HerobrineMod.MODID, "textures/entity/brother.png");
    }
}
