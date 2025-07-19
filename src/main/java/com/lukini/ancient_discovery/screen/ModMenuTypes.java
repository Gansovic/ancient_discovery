package com.lukini.ancient_discovery.screen;


import com.lukini.ancient_discovery.AncientDiscovery;
import com.lukini.ancient_discovery.screen.custom.AncientTableMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, AncientDiscovery.MOD_ID);

    public static final RegistryObject<MenuType<AncientTableMenu>> ANCIENT_TABLE_MENU =
            MENUS.register("ancient_table_menu", () -> IForgeMenuType.create(AncientTableMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
