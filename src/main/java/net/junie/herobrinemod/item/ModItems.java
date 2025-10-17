package net.junie.herobrinemod.item;

import net.junie.herobrinemod.HerobrineMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HerobrineMod.MODID);

    //creates herobrine sword as an item, does not have any code yet. for if we have time for a drop
    public static final RegistryObject<Item> HEROBRINE_SWORD = ITEMS.register(
            "herobrine_sword",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
