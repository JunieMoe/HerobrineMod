package net.junie.herobrinemod.registry;

import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.entity.HerobrineEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class ModEntities {

    // Deferred register for entities
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HerobrineMod.MODID);

    // Herobrine entity registration
    public static final RegistryObject<EntityType<HerobrineEntity>> HEROBRINE =
            ENTITIES.register("herobrine",
                    () -> EntityType.Builder.of(HerobrineEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.95f) // width, height
                            .build("herobrine")
            );

    // Attribute supplier for Herobrine
    public static AttributeSupplier.Builder createAttributes() {
        return HerobrineEntity.createAttributes();
    }

    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }

}
