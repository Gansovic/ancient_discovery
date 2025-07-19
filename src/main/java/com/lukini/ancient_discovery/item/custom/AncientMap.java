package com.lukini.ancient_discovery.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AncientMap extends MapItem {

    public AncientMap(Item.Properties properties) {
        super(properties);
    }

    // Guarda la posición de la tumba en el NBT del mapa
    public static void setTombPos(ItemStack stack, BlockPos pos) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("TombX", pos.getX());
        tag.putInt("TombY", pos.getY());
        tag.putInt("TombZ", pos.getZ());

        // Guarda en el componente personalizado del item
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }


    public static BlockPos getTombPos(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null) return null;

        CompoundTag tag = data.copyTag(); // o .tag() si no quieres copiarlo
        if (tag.contains("TombX") && tag.contains("TombY") && tag.contains("TombZ")) {
            return new BlockPos(tag.getInt("TombX"), tag.getInt("TombY"), tag.getInt("TombZ"));
        }
        return null;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag) {

        BlockPos pos = AncientMap.getTombPos(pStack);
        if (pos != null) {
            pTooltipComponents.add(Component.literal("§6Tumba descubierta en:"));
            pTooltipComponents.add(Component.literal("§7X: " + pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getZ()));
        } else {
            pTooltipComponents.add(Component.literal("§8No contiene coordenadas."));
        }

        //super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
