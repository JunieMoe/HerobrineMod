package net.junie.herobrinemod.sound;

import net.junie.herobrinemod.HerobrineMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, HerobrineMod.MODID);

    public static final RegistryObject<SoundEvent> HEROBRINE_BOSS_THEME =
            SOUND_EVENTS.register("music.boss.herobrinetheme",
                    () -> SoundEvent.createVariableRangeEvent(
                            new ResourceLocation(HerobrineMod.MODID, "music.boss.herobrinetheme"))
            );

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
