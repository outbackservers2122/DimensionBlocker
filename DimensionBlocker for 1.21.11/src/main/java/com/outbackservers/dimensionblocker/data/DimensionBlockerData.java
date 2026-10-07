package com.outbackservers.dimensionblocker.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class DimensionBlockerData extends SavedData {

    private static final String DATA_NAME = "dimensionblocker";

    public static final SavedDataType<DimensionBlockerData> TYPE =
            new SavedDataType<>(
                    DATA_NAME,
                    DimensionBlockerData::new,
                    RecordCodecBuilder.create(instance ->
                            instance.group(
                                    Codec.BOOL
                                            .fieldOf("NetherLocked")
                                            .forGetter(data -> data.netherLocked),

                                    Codec.BOOL
                                            .fieldOf("EndLocked")
                                            .forGetter(data -> data.endLocked),

                                    Codec.LONG
                                            .fieldOf("NetherUnlockTime")
                                            .forGetter(data -> data.netherUnlockTime),

                                    Codec.LONG
                                            .fieldOf("EndUnlockTime")
                                            .forGetter(data -> data.endUnlockTime)
                            ).apply(
                                    instance,
                                    DimensionBlockerData::new
                            )
                    )
            );

    private boolean netherLocked;
    private boolean endLocked;

    private long netherUnlockTime;
    private long endUnlockTime;

    public DimensionBlockerData() {
        this(true, true, 0L, 0L);
    }

    public DimensionBlockerData(
            boolean netherLocked,
            boolean endLocked,
            long netherUnlockTime,
            long endUnlockTime
    ) {
        this.netherLocked = netherLocked;
        this.endLocked = endLocked;
        this.netherUnlockTime = netherUnlockTime;
        this.endUnlockTime = endUnlockTime;
    }

    public boolean isNetherLocked() {
        updateTimers();
        return netherLocked;
    }

    public boolean isEndLocked() {
        updateTimers();
        return endLocked;
    }

    public long getNetherUnlockTime() {
        return netherUnlockTime;
    }

    public long getEndUnlockTime() {
        return endUnlockTime;
    }

    public void setNetherLocked(boolean locked) {
        this.netherLocked = locked;
        this.netherUnlockTime = 0L;
        setDirty();
    }

    public void setEndLocked(boolean locked) {
        this.endLocked = locked;
        this.endUnlockTime = 0L;
        setDirty();
    }

    public void setNetherUnlockTime(long unlockTime) {
        this.netherLocked = true;
        this.netherUnlockTime = unlockTime;
        setDirty();
    }

    public void setEndUnlockTime(long unlockTime) {
        this.endLocked = true;
        this.endUnlockTime = unlockTime;
        setDirty();
    }

    private void updateTimers() {
        long now = System.currentTimeMillis();

        if (netherLocked
                && netherUnlockTime > 0L
                && now >= netherUnlockTime) {

            netherLocked = false;
            netherUnlockTime = 0L;
            setDirty();
        }

        if (endLocked
                && endUnlockTime > 0L
                && now >= endUnlockTime) {

            endLocked = false;
            endUnlockTime = 0L;
            setDirty();
        }
    }

    public static DimensionBlockerData get(MinecraftServer server) {
        return server.overworld()
                .getDataStorage()
                .computeIfAbsent(TYPE);
    }
}