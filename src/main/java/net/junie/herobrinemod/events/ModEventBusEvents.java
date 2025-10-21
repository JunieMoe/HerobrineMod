package net.junie.herobrinemod.events;

import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.entity.ModEntities;
import net.junie.herobrinemod.entity.custom.BrotherEntity;
import net.junie.herobrinemod.entity.custom.HerobrineEntity;
import net.junie.herobrinemod.entity.custom.MikuEntity;
import net.junie.herobrinemod.entity.custom.TetoEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HerobrineMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.HEROBRINE.get(), HerobrineEntity.createAttributes().build());
        event.put(ModEntities.BROTHER.get(), BrotherEntity.createAttributes().build());
        event.put(ModEntities.MIKU.get(), MikuEntity.createAttributes().build());
        event.put(ModEntities.TETO.get(), TetoEntity.createAttributes().build());
    }
}