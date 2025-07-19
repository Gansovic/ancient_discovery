package com.lukini.ancient_discovery.knowledge;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerKnowledgeProvider implements ICapabilitySerializable<CompoundTag>, ICapabilityProvider {
    private final PlayerKnowledgeCapability backend = new PlayerKnowledgeCapability();
    private final LazyOptional<PlayerKnowledgeCapability> optional = LazyOptional.of(() -> backend);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
        return KnowledgeCapabilities.PLAYER_KNOWLEDGE.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider registryAccess) {
        return backend.serializeNBT();
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider registryAccess, CompoundTag nbt) {
        backend.deserializeNBT(nbt);
    }
}
