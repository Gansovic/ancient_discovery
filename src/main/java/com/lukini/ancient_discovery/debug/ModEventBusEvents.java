package com.lukini.ancient_discovery.debug;

import com.lukini.ancient_discovery.AncientDiscovery;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AncientDiscovery.MOD_ID)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(AncientCommands.register());
    }
}
