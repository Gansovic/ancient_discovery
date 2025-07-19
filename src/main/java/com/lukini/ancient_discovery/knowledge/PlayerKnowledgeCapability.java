// --- PlayerKnowledgeCapability.java ---
package com.lukini.ancient_discovery.knowledge;

import com.lukini.ancient_discovery.world.AncientTomb;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerKnowledgeCapability {
    private int knowledge = 0;
    private int currentMilestone = 0;
    private boolean acceptedTutankamonCurse = false;
    private BlockPos tutankamonTombPos = null;

    public int getKnowledge() {
        return knowledge;
    }


    public void addKnowledge(int amount) {
        this.knowledge += amount;
        // No milestone triggered here – handled on item input like AncientRelic
    }

    public void resetKnowledgeMilestone() {
        this.currentMilestone = 0;
        this.knowledge = 0;
    }

    public void tryTriggerMilestone(ServerPlayer player, String relicId) {
        player.sendSystemMessage(Component.literal("DEBUG: Reliquia = " + relicId));
        player.sendSystemMessage(Component.literal("DEBUG: Knowledge = " + knowledge));
        player.sendSystemMessage(Component.literal("DEBUG: Milestone = " + currentMilestone));

        if (currentMilestone == 0 && knowledge >= 10 && relicId.equals("ancient_discovery:anubis_gem")) {
            currentMilestone = 1;
            player.sendSystemMessage(Component.literal("§6[Milestone]§r Has alcanzado el §dMilestone de Tutankamón§r. El Ancient Table brilla misteriosamente..."));
            lightTable(player);
            AncientTomb.spawnTutankamonTomb(player);
            player.getInventory().add(Items.DIAMOND.getDefaultInstance());
        }
    }


    private void lightTable(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        BlockPos tablePos = player.blockPosition();
        level.playSound(null, tablePos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.5f, 1f);
        // Aquí podrías agregar partículas si lo deseas
    }


    public int getCurrentMilestone() {
        return currentMilestone;
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("knowledge", knowledge);
        tag.putInt("milestone", currentMilestone);
        tag.putBoolean("curse", acceptedTutankamonCurse);
        if (tutankamonTombPos != null) {
            tag.putInt("tomb_x", tutankamonTombPos.getX());
            tag.putInt("tomb_y", tutankamonTombPos.getY());
            tag.putInt("tomb_z", tutankamonTombPos.getZ());
        }

        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        knowledge = tag.getInt("knowledge");
        currentMilestone = tag.getInt("milestone");
        acceptedTutankamonCurse = tag.getBoolean("curse");
        if (tag.contains("tomb_x")) {
            int x = tag.getInt("tomb_x");
            int y = tag.getInt("tomb_y");
            int z = tag.getInt("tomb_z");
            tutankamonTombPos = new BlockPos(x, y, z);
        }

    }

    public static PlayerKnowledgeCapability get(Player player) {
        return player.getCapability(KnowledgeCapabilities.PLAYER_KNOWLEDGE).orElseThrow(() ->
                new IllegalStateException("Knowledge capability missing for player " + player.getName().getString()));
    }
}
