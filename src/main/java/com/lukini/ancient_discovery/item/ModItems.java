package com.lukini.ancient_discovery.item;

import com.lukini.ancient_discovery.AncientDiscovery;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AncientDiscovery.MOD_ID);

    public static final RegistryObject<Item> ANUBIS_GEM = registerItem("anubis_gem",
            Item::new);
    public static final RegistryObject<Item> RAW_ALEXANDRITE = registerItem("raw_alexandrite",
            Item::new);

    public static RegistryObject<Item> registerItem(String name, Function<Item.Properties, Item> function) {
        return ModItems.ITEMS.register(name, () -> function.apply(new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
