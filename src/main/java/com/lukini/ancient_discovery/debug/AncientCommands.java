package com.lukini.ancient_discovery.debug;

import com.lukini.ancient_discovery.knowledge.PlayerKnowledgeCapability;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

public class AncientCommands {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("reset_ancient")
                .requires(source -> source.hasPermission(2)) // nivel de permisos (2 = operador)
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    PlayerKnowledgeCapability cap = PlayerKnowledgeCapability.get(player);
                    cap.resetKnowledgeMilestone(player);
                    player.sendSystemMessage(Component.literal("§c[Ancient Table] Progreso reiniciado."));
                    return 1;
                });
    }
}
