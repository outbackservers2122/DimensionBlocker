package com.outbackservers.dimensionblocker.event;

import com.outbackservers.dimensionblocker.data.DimensionBlockerData;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;

import static com.outbackservers.dimensionblocker.DimensionBlocker.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public class DimensionBlockerTeleport {

    @SubscribeEvent
    public static void onTravelToDimension(
            EntityTravelToDimensionEvent event
    ) {

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        ResourceKey<Level> destination = event.getDimension();

        // Only control The Nether and The End.
        if (!destination.equals(Level.NETHER)
                && !destination.equals(Level.END)) {
            return;
        }

        // Gamemasters and higher permissions can bypass the blocker.
        if (player.permissions().hasPermission(
                Permissions.COMMANDS_GAMEMASTER
        )) {
            return;
        }

        MinecraftServer server = player.level().getServer();

        if (server == null) {
            return;
        }

        DimensionBlockerData data =
                DimensionBlockerData.get(server);

        boolean locked;

        if (destination.equals(Level.NETHER)) {
            locked = data.isNetherLocked();
        } else {
            locked = data.isEndLocked();
        }

        if (!locked) {
            return;
        }

        event.setCanceled(true);

        long unlockTime;

        if (destination.equals(Level.NETHER)) {
            unlockTime = data.getNetherUnlockTime();
        } else {
            unlockTime = data.getEndUnlockTime();
        }

        String dimensionName =
                destination.equals(Level.NETHER)
                        ? "The Nether"
                        : "The End";

        player.sendSystemMessage(
                Component.literal(
                        "§c"
                                + dimensionName
                                + " is currently locked."
                )
        );

        if (unlockTime > 0L) {

            long remaining =
                    unlockTime - System.currentTimeMillis();

            if (remaining > 0L) {

                player.sendSystemMessage(
                        Component.literal(
                                "§eUnlocks in §f"
                                        + formatDuration(remaining)
                                        + "§e."
                        )
                );

            } else {

                player.sendSystemMessage(
                        Component.literal(
                                "§eUnlocking soon..."
                        )
                );
            }

        } else {

            player.sendSystemMessage(
                    Component.literal(
                            "§eUnlocking soon..."
                    )
            );
        }
    }

    private static String formatDuration(long milliseconds) {

        if (milliseconds < 0L) {
            milliseconds = 0L;
        }

        long totalSeconds = milliseconds / 1_000L;

        long days = totalSeconds / 86_400L;
        totalSeconds %= 86_400L;

        long hours = totalSeconds / 3_600L;
        totalSeconds %= 3_600L;

        long minutes = totalSeconds / 60L;

        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result.append(days)
                    .append(" day");

            if (days != 1) {
                result.append("s");
            }
        }

        if (hours > 0) {
            appendSeparator(result);

            result.append(hours)
                    .append(" hour");

            if (hours != 1) {
                result.append("s");
            }
        }

        if (minutes > 0) {
            appendSeparator(result);

            result.append(minutes)
                    .append(" minute");

            if (minutes != 1) {
                result.append("s");
            }
        }

        if (result.isEmpty()) {
            result.append("less than 1 minute");
        }

        return result.toString();
    }

    private static void appendSeparator(StringBuilder result) {

        if (!result.isEmpty()) {
            result.append(", ");
        }
    }
}