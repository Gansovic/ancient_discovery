package com.lukini.ancient_discovery.block.entity;

import com.lukini.ancient_discovery.AncientDiscovery;
import com.lukini.ancient_discovery.block.ModBlocks;
import com.lukini.ancient_discovery.block.entity.custom.AncientTableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AncientDiscovery.MOD_ID);

    public static final RegistryObject<BlockEntityType<AncientTableBlockEntity>> ANCIENT_TABLE_BE =
            BLOCK_ENTITIES.register("ancient_table_be", () ->
                    BlockEntityType.Builder.of(AncientTableBlockEntity::new, ModBlocks.ANCIENT_TABLE.get()).build(null)
            );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}