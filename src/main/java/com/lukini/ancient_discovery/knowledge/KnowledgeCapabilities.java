package com.lukini.ancient_discovery.knowledge;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class KnowledgeCapabilities {
    public static final Capability<PlayerKnowledgeCapability> PLAYER_KNOWLEDGE =
            CapabilityManager.get(new CapabilityToken<PlayerKnowledgeCapability>() {
            });
}
