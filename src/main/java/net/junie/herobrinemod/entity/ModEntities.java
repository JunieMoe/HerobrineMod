package net.junie.herobrinemod.entity;

import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.entity.custom.BrotherEntity;
import net.junie.herobrinemod.entity.custom.HerobrineEntity;
import net.junie.herobrinemod.entity.custom.MikuEntity;
import net.junie.herobrinemod.entity.custom.TetoEntity;
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

    public static final RegistryObject<EntityType<HerobrineEntity>> HEROBRINE =
            ENTITIES.register("herobrine",
                    () -> EntityType.Builder.of(HerobrineEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.95f)
                            .build("herobrine")
            );

    public static final RegistryObject<EntityType<BrotherEntity>> BROTHER =
            ENTITIES.register("brother",
                    () -> EntityType.Builder.of(BrotherEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.95f)
                            .build("brother")
            );

    public static final RegistryObject<EntityType<MikuEntity>> MIKU =
            ENTITIES.register("miku",
                    () -> EntityType.Builder.of(MikuEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 2.5f)
                            .build("miku")
            );

    public static final RegistryObject<EntityType<TetoEntity>> TETO =
            ENTITIES.register("teto",
                    () -> EntityType.Builder.of(TetoEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 2f)
                            .build("teto")
            );

    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }

}
