package com.lukini.ancient_discovery.world;

import com.lukini.ancient_discovery.item.ModItems;
import com.lukini.ancient_discovery.item.custom.AncientMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class AncientTomb {

    static BlockPos lastPos;

    public static void spawnTutankamonTomb(ServerPlayer player, Level world) {
        ServerLevel level = player.serverLevel();
        BlockPos basePos = player.blockPosition().offset(5, 0, 5);

        // Genera estructura simple
        for (int y = 0; y < 4; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos pos = basePos.offset(x, y, z);
                    BlockState state = (y == 3) ? Blocks.SANDSTONE.defaultBlockState() : Blocks.SAND.defaultBlockState();
                    level.setBlockAndUpdate(pos, state);
                }
            }
        }

        // Sonido y efectos
        level.playSound(null, basePos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.5f, 1f);

        // Entrega de recompensas
        lastPos = basePos;
        rewardPlayer(player, basePos);
    }


    public static void rewardPlayer(ServerPlayer player, BlockPos tombPos) {
        player.getInventory().add(new ItemStack(Items.DIAMOND));

        ItemStack map = createAncientMap(player.level(), tombPos);
        player.getInventory().add(map);

    }

    public static ItemStack createAncientMap(Level level, BlockPos tombPos) {
        ItemStack stack = MapItem.create(level, tombPos.getX(), tombPos.getZ(), (byte)1, true, true);
        ItemStack ancientMap = new ItemStack(ModItems.ANCIENT_MAP.get());

        // Copia el MAP_ID al nuevo stack
        ancientMap.set(DataComponents.MAP_ID, stack.get(DataComponents.MAP_ID));

        // Guarda la posición de la tumba
        AncientMap.setTombPos(ancientMap, tombPos);

        return ancientMap;
    }
}
