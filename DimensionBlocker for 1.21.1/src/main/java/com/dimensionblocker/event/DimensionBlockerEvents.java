package com.outbackservers.dimensionblocker.event;

import com.outbackservers.dimensionblocker.command.DimensionBlockerCommands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import static com.outbackservers.dimensionblocker.DimensionBlocker.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public class DimensionBlockerEvents {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        DimensionBlockerCommands.register(event);
    }
}