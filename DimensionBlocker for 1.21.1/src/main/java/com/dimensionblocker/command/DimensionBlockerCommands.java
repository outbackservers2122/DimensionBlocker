package com.outbackservers.dimensionblocker.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.dimensionblocker.data.DimensionBlockerData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class DimensionBlockerCommands {

    public static void register(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("dimensionblocker")
                        .requires(source -> source.hasPermission(2))

                        // /dimensionblocker end
                        .then(Commands.literal("end")
                                .then(Commands.literal("off")
                                        .executes(context ->
                                                setOff(context.getSource(), false)
                                        )
                                )
                                .then(Commands.literal("on")
                                        .executes(context ->
                                                setOn(context.getSource(), false)
                                        )
                                )
                                .then(Commands.literal("timer")
                                        .then(Commands.argument(
                                                                "time",
                                                                StringArgumentType.word()
                                                        )
                                                        .executes(context ->
                                                                setTimer(
                                                                        context.getSource(),
                                                                        false,
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
                        .then(Commands.literal("nether")
                                .then(Commands.literal("off")
                                        .executes(context ->
                                                setOff(context.getSource(), true)
                                        )
                                )
                                .then(Commands.literal("on")
                                        .executes(context ->
                                                setOn(context.getSource(), true)
                                        )
                                )
                                .then(Commands.literal("timer")
                                        .then(Commands.argument(
                                                                "time",
                                                                StringArgumentType.word()
                                                        )
                                                        .executes(context ->
                                                                setTimer(
                                                                        context.getSource(),
                                                                        true,
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

    private static int setOff(
            CommandSourceStack source,
            boolean nether
    ) {
        MinecraftServer server = source.getServer();
        DimensionBlockerData data = DimensionBlockerData.get(server);

        if (nether) {
            data.setNetherLocked(true);
        } else {
            data.setEndLocked(true);
        }

        String dimension = nether ? "Nether" : "The End";

        source.sendSuccess(
                () -> Component.literal(
                        "§c" + dimension + " is now locked. §7Only OPs can enter."
                ),
                true
        );

        return 1;
    }

    private static int setOn(
            CommandSourceStack source,
            boolean nether
    ) {
        MinecraftServer server = source.getServer();
        DimensionBlockerData data = DimensionBlockerData.get(server);

        if (nether) {
            data.setNetherLocked(false);
        } else {
            data.setEndLocked(false);
        }

        String dimension = nether ? "Nether" : "The End";

        source.sendSuccess(
                () -> Component.literal(
                        "§a" + dimension + " is now unlocked. §7Everyone can enter."
                ),
                true
        );

        return 1;
    }

    private static int setTimer(
            CommandSourceStack source,
            boolean nether,
            String input
    ) {
        long duration = parseDuration(input);

        if (duration <= 0L) {
            source.sendFailure(
                    Component.literal(
                            "§cInvalid timer. Examples: 30s, 10m, 2h, 2d, 2d12h30m"
                    )
            );

            return 0;
        }

        MinecraftServer server = source.getServer();
        DimensionBlockerData data = DimensionBlockerData.get(server);

        long unlockTime = System.currentTimeMillis() + duration;

        if (nether) {
            data.setNetherUnlockTime(unlockTime);
        } else {
            data.setEndUnlockTime(unlockTime);
        }

        String dimension = nether ? "Nether" : "The End";

        source.sendSuccess(
                () -> Component.literal(
                        "§e" + dimension
                                + " is locked. §7It will unlock in "
                                + formatDuration(duration)
                                + "."
                ),
                true
        );

        return 1;
    }

    private static long parseDuration(String input) {
        if (input == null || input.isEmpty()) {
            return -1L;
        }

        long total = 0L;
        int index = 0;

        try {
            while (index < input.length()) {

                int numberStart = index;

                while (index < input.length()
                        && Character.isDigit(input.charAt(index))) {
                    index++;
                }

                if (numberStart == index) {
                    return -1L;
                }

                long number = Long.parseLong(
                        input.substring(numberStart, index)
                );

                if (index >= input.length()) {
                    return -1L;
                }

                char unit = Character.toLowerCase(
                        input.charAt(index)
                );

                index++;

                long multiplier;

                switch (unit) {
                    case 's' -> multiplier = 1000L;
                    case 'm' -> multiplier = 60_000L;
                    case 'h' -> multiplier = 3_600_000L;
                    case 'd' -> multiplier = 86_400_000L;
                    default -> {
                        return -1L;
                    }
                }

                if (number > Long.MAX_VALUE / multiplier) {
                    return -1L;
                }

                long amount = number * multiplier;

                if (total > Long.MAX_VALUE - amount) {
                    return -1L;
                }

                total += amount;
            }
        } catch (NumberFormatException exception) {
            return -1L;
        }

        return total;
    }

    private static String formatDuration(long milliseconds) {
        long totalSeconds = milliseconds / 1000L;

        long days = totalSeconds / 86_400L;
        totalSeconds %= 86_400L;

        long hours = totalSeconds / 3_600L;
        totalSeconds %= 3_600L;

        long minutes = totalSeconds / 60L;
        long seconds = totalSeconds % 60L;

        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result.append(days).append(" day");

            if (days != 1) {
                result.append("s");
            }
        }

        if (hours > 0) {
            appendSeparator(result);
            result.append(hours).append(" hour");

            if (hours != 1) {
                result.append("s");
            }
        }

        if (minutes > 0) {
            appendSeparator(result);
            result.append(minutes).append(" minute");

            if (minutes != 1) {
                result.append("s");
            }
        }

        if (seconds > 0 || result.isEmpty()) {
            appendSeparator(result);
            result.append(seconds).append(" second");

            if (seconds != 1) {
                result.append("s");
            }
        }

        return result.toString();
    }

    private static void appendSeparator(StringBuilder result) {
        if (!result.isEmpty()) {
            result.append(", ");
        }
    }
}