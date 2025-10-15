package net.junie.herobrinemod.item;

import net.junie.herobrinemod.HerobrineMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HerobrineMod.MODID);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
