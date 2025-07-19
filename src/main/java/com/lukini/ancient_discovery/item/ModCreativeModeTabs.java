package com.lukini.ancient_discovery.item;

import com.lukini.ancient_discovery.AncientDiscovery;
import com.lukini.ancient_discovery.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AncientDiscovery.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ALEXANDRITE_ITEMS_TAB = CREATIVE_MODE_TABS.register("ancient_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ANUBIS_GEM.get()))
                    .title(Component.translatable("creativetab.ancient_discovery.ancient_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ANUBIS_GEM.get());
                        output.accept(ModItems.ANUBIS_WAND.get());

                        output.accept(ModItems.RA_GEM.get());
                        output.accept(ModItems.RA_WAND.get());



                    }).build());

    public static final RegistryObject<CreativeModeTab> ALEXANDRITE_BLOCKS_TAB = CREATIVE_MODE_TABS.register("ancient_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.ANCIENT_SANDSTONE.get()))
                    .withTabsBefore(ALEXANDRITE_ITEMS_TAB.getId())
                    .title(Component.translatable("creativetab.ancient_discovery.ancient_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.ANCIENT_SANDSTONE.get());
                        output.accept(ModBlocks.ANCIENT_TABLE.get());
                        output.accept(ModItems.PAPYRUS.get());

                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}