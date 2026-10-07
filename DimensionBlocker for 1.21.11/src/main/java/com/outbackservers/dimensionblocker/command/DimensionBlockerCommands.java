package com.outbackservers.dimensionblocker.command;

import com.outbackservers.dimensionblocker.data.DimensionBlockerData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class DimensionBlockerCommands {

    public static void register(RegisterCommandsEvent event) {

        CommandDispatcher<CommandSourceStack> dispatcher =
                event.getDispatcher();

        dispatcher.register(
                Commands.literal("dimensionblocker")
                        .requires(
                                Commands.hasPermission(
                                        Commands.LEVEL_GAMEMASTERS
                                )
                        )

                        // /dimensionblocker end
                        .then(
                                Commands.literal("end")

                                        // /dimensionblocker end on
                                        .then(
                                                Commands.literal("on")
                                                        .executes(context ->
                                                                setEnd(
                                                                        context.getSource(),
                                                                        false
                                                                )
                                                        )
                                        )

                                        // /dimensionblocker end off
                                        .then(
                                                Commands.literal("off")
                                                        .executes(context ->
                                                                setEnd(
                                                                        context.getSource(),
                                                                        true
                                                                )
                                                        )
                                        )

                                        // /dimensionblocker end timer <time>
                                        .then(
                                                Commands.literal("timer")
                                                        .then(
                                                                Commands.argument(
                                                                                "time",
                                                                                StringArgumentType.word()
                                                                        )
                                                                        .executes(context ->
                                                                                setEndTimer(
                                                                                        context.getSource(),
                                                                                        StringArgumentType.getString(
                                                                                                context,
                                                                                                "time"
                                                                                        )
                                                                                )
                                                                        )
                                                        )
                                        )
                        )

                        // /dimensionblocker nether
                        .then(
                                Commands.literal("nether")

                                        // /dimensionblocker nether on
                                        .then(
                                                Commands.literal("on")
                                                        .executes(context ->
                                                                setNether(
                                                                        context.getSource(),
                                                                        false
                                                                )
                                                        )
                                        )

                                        // /dimensionblocker nether off
                                        .then(
                                                Commands.literal("off")
                                                        .executes(context ->
                                                                setNether(
                                                                        context.getSource(),
                                                                        true
                                                                )
                                                        )
                                        )

                                        // /dimensionblocker nether timer <time>
                                        .then(
                                                Commands.literal("timer")
                                                        .then(
                                                                Commands.argument(
                                                                                "time",
                                                                                StringArgumentType.word()
                                                                        )
                                                                        .executes(context ->
                                                                                setNetherTimer(
                                                                                        context.getSource(),
                                                                                        StringArgumentType.getString(
                                                                                                context,
                                                                                                "time"
                                                                                        )
                                                                                )
                                                                        )
                                                        )
                                        )
                        )
        );
    }

    private static int setNether(
            CommandSourceStack source,
            boolean locked
    ) {

        MinecraftServer server = source.getServer();

        DimensionBlockerData data =
                DimensionBlockerData.get(server);

        data.setNetherLocked(locked);

        if (locked) {
            source.sendSuccess(
                    () -> Component.literal(
                            "The Nether is now §clocked§r."
                    ),
                    true
            );
        } else {
            source.sendSuccess(
                    () -> Component.literal(
                            "The Nether is now §aunlocked§r."
                    ),
                    true
            );
        }

        return 1;
    }

    private static int setEnd(
            CommandSourceStack source,
            boolean locked
    ) {

        MinecraftServer server = source.getServer();

        DimensionBlockerData data =
                DimensionBlockerData.get(server);

        data.setEndLocked(locked);

        if (locked) {
            source.sendSuccess(
                    () -> Component.literal(
                            "The End is now §clocked§r."
                    ),
                    true
            );
        } else {
            source.sendSuccess(
                    () -> Component.literal(
                            "The End is now §aunlocked§r."
                    ),
                    true
            );
        }

        return 1;
    }

    private static int setNetherTimer(
            CommandSourceStack source,
            String time
    ) {

        long duration = parseDuration(time);

        if (duration <= 0L) {
            source.sendFailure(
                    Component.literal(
                            "Invalid time. Examples: 30s, 10m, 2h, 2d, 2d12h30m"
                    )
            );

            return 0;
        }

        MinecraftServer server = source.getServer();

        DimensionBlockerData data =
                DimensionBlockerData.get(server);

        long unlockTime =
                System.currentTimeMillis() + duration;

        data.setNetherUnlockTime(unlockTime);

        source.sendSuccess(
                () -> Component.literal(
                        "The Nether will unlock in §e"
                                + formatDuration(duration)
                                + "§r."
                ),
                true
        );

        return 1;
    }

    private static int setEndTimer(
            CommandSourceStack source,
            String time
    ) {

        long duration = parseDuration(time);

        if (duration <= 0L) {
            source.sendFailure(
                    Component.literal(
                            "Invalid time. Examples: 30s, 10m, 2h, 2d, 2d12h30m"
                    )
            );

            return 0;
        }

        MinecraftServer server = source.getServer();

        DimensionBlockerData data =
                DimensionBlockerData.get(server);

        long unlockTime =
                System.currentTimeMillis() + duration;

        data.setEndUnlockTime(unlockTime);

        source.sendSuccess(
                () -> Component.literal(
                        "The End will unlock in §e"
                                + formatDuration(duration)
                                + "§r."
                ),
                true
        );

        return 1;
    }

    private static long parseDuration(String input) {

        if (input == null || input.isBlank()) {
            return -1L;
        }

        long totalMilliseconds = 0L;

        StringBuilder number = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {

            char character = input.charAt(i);

            if (Character.isDigit(character)) {
                number.append(character);
                continue;
            }

            if (number.isEmpty()) {
                return -1L;
            }

            long value;

            try {
                value = Long.parseLong(number.toString());
            } catch (NumberFormatException exception) {
                return -1L;
            }

            number.setLength(0);

            long multiplier;

            switch (character) {

                case 's':
                case 'S':
                    multiplier = 1_000L;
                    break;

                case 'm':
                case 'M':
                    multiplier = 60_000L;
                    break;

                case 'h':
                case 'H':
                    multiplier = 3_600_000L;
                    break;

                case 'd':
                case 'D':
                    multiplier = 86_400_000L;
                    break;

                default:
                    return -1L;
            }

            try {
                totalMilliseconds =
                        Math.addExact(
                                totalMilliseconds,
                                Math.multiplyExact(
                                        value,
                                        multiplier
                                )
                        );
            } catch (ArithmeticException exception) {
                return -1L;
            }
        }

        if (!number.isEmpty()) {
            return -1L;
        }

        return totalMilliseconds;
    }

    private static String formatDuration(long milliseconds) {

        long totalSeconds =
                milliseconds / 1_000L;

        long days =
                totalSeconds / 86_400L;

        totalSeconds %= 86_400L;

        long hours =
                totalSeconds / 3_600L;

        totalSeconds %= 3_600L;

        long minutes =
                totalSeconds / 60L;

        totalSeconds %= 60L;

        long seconds =
                totalSeconds;

        StringBuilder result =
                new StringBuilder();

        if (days > 0) {
            result.append(days).append("d");
        }

        if (hours > 0) {
            result.append(hours).append("h");
        }

        if (minutes > 0) {
            result.append(minutes).append("m");
        }

        if (seconds > 0 || result.isEmpty()) {
            result.append(seconds).append("s");
        }

        return result.toString();
    }
}