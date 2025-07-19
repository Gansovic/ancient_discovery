package com.lukini.ancient_discovery.block;

import com.lukini.ancient_discovery.AncientDiscovery;
import com.lukini.ancient_discovery.block.custom.AncientTableBlock;
import com.lukini.ancient_discovery.block.custom.HieroglyphBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.lukini.ancient_discovery.item.ModItems;

import java.util.function.Function;

import static net.minecraft.world.level.block.Blocks.SAND;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AncientDiscovery.MOD_ID);

    public static final RegistryObject<Block> ANCIENT_SANDSTONE = registerBlock("ancient_sandstone",
            (properties) -> new Block(properties
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> ANCIENT_TABLE = registerBlock("ancient_table",
            (properties) -> new AncientTableBlock(properties.noOcclusion()));

    public static final RegistryObject<Block> HIEROGLYPH_BLOCK = registerBlock("hieroglyph_block",
            (properties) -> new HieroglyphBlock(properties));

    public static final RegistryObject<Block> HIEROGLYPH_SAND = registerBlock("hieroglyph_sand",
            (properties) -> new BrushableBlock(
                    HIEROGLYPH_BLOCK.get(), // bloque que se revela al cepillar
                    SoundEvents.BRUSH_SAND, // sonido mientras cepillas
                    SoundEvents.BRUSH_SAND, // sonido al terminar
                    properties
                            .mapColor(MapColor.SAND)
                            .strength(0.5F)
                            .pushReaction(PushReaction.DESTROY)
                            .sound(SoundType.SAND)
                            .instrument(NoteBlockInstrument.SNARE)
            ));




    private static <T extends Block> RegistryObject<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        RegistryObject<T> toReturn = BLOCKS.register(name,
                () -> function.apply(BlockBehaviour.Properties.of()));
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
