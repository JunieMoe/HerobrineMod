package net.junie.herobrinemod.events;

import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.entity.ModEntities;
import net.junie.herobrinemod.entity.custom.HerobrineEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HerobrineMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.HEROBRINE.get(), HerobrineEntity.createAttributes().build());
    }
}
