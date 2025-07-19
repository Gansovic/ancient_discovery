package com.lukini.ancient_discovery.item.custom;

import com.lukini.ancient_discovery.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class RaWandItem extends Item {

    public RaWandItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) return InteractionResult.SUCCESS;

        Player player = context.getPlayer();
        BlockPos clickedPos = context.getClickedPos();
        Direction face = context.getClickedFace();

        // Offset en la dirección de la cara clickeada
        BlockPos basePos = clickedPos.relative(face);

        // Selección del eje horizontal según la cara
        Direction.Axis axis = face.getAxis().isVertical() ? Direction.Axis.X : Direction.Axis.Y;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos targetPos;

                if (face == Direction.UP || face == Direction.DOWN) {
                    targetPos = basePos.offset(dx, 0, dz);
                } else if (face == Direction.NORTH || face == Direction.SOUTH) {
                    targetPos = basePos.offset(dx, dz, 0);
                } else { // EAST or WEST
                    targetPos = basePos.offset(0, dx, dz);
                }

                if (level.isEmptyBlock(targetPos) || level.getBlockState(targetPos).canBeReplaced()) {
                    BlockState sandstone = ModBlocks.ANCIENT_SANDSTONE.get().defaultBlockState();
                    level.setBlock(targetPos, sandstone, 3);
                }
            }
        }

        level.playSound(null, basePos, SoundEvents.STONE_PLACE, SoundSource.PLAYERS, 1.0f, 1.0f);
        return InteractionResult.SUCCESS;
    }
}
