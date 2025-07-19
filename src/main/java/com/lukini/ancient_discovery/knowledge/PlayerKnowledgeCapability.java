// --- PlayerKnowledgeCapability.java ---
package com.lukini.ancient_discovery.knowledge;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerKnowledgeCapability {
    private int knowledge = 0;
    private int currentMilestone = 0;
    private boolean acceptedTutankamonCurse = false;

    public int getKnowledge() {
        return knowledge;
    }

    public void addKnowledge(int amount) {
        knowledge += amount;
    }

    public void setKnowledge(int knowledge) {
        this.knowledge = knowledge;
    }

    public int getCurrentMilestone() {
        return currentMilestone;
    }

    public void advanceMilestone() {
        currentMilestone++;
    }

    public void setCurrentMilestone(int milestone) {
        this.currentMilestone = milestone;
    }

    public boolean hasAcceptedTutankamonCurse() {
        return acceptedTutankamonCurse;
    }

    public void acceptTutankamonCurse() {
        this.acceptedTutankamonCurse = true;
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("knowledge", knowledge);
        tag.putInt("milestone", currentMilestone);
        tag.putBoolean("curse", acceptedTutankamonCurse);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        knowledge = tag.getInt("knowledge");
        currentMilestone = tag.getInt("milestone");
        acceptedTutankamonCurse = tag.getBoolean("curse");
    }

    public static PlayerKnowledgeCapability get(Player player) {
        return player.getCapability(KnowledgeCapabilities.PLAYER_KNOWLEDGE).orElseThrow(() ->
                new IllegalStateException("Knowledge capability missing for player " + player.getName().getString()));
    }
}
