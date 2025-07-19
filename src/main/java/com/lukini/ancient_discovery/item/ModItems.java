package com.lukini.ancient_discovery.item;

import com.lukini.ancient_discovery.AncientDiscovery;
import com.lukini.ancient_discovery.item.custom.AncientMap;
import com.lukini.ancient_discovery.item.custom.AncientRelic;
import com.lukini.ancient_discovery.item.custom.AnubisWandItem;
import com.lukini.ancient_discovery.item.custom.RaWandItem;
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
            AncientRelic::new);
    public static final RegistryObject<Item> ANUBIS_WAND = registerItem("anubis_wand",
            (properties) -> new AnubisWandItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ANCIENT_MAP = ITEMS.register("ancient_map",
            () -> new AncientMap(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> RA_GEM = registerItem("ra_gem",
            Item::new);
    public static final RegistryObject<Item> RA_WAND = registerItem("ra_wand",
            (properties) -> new RaWandItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PAPYRUS = registerItem("papyrus",
            (properties) -> new Item(new Item.Properties().stacksTo(16)));

    public static RegistryObject<Item> registerItem(String name, Function<Item.Properties, Item> function) {
        return ModItems.ITEMS.register(name, () -> function.apply(new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
