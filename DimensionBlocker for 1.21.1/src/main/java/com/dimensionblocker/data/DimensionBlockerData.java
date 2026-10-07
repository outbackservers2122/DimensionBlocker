package com.dimensionblocker.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class DimensionBlockerData extends SavedData {

    private static final String DATA_NAME = "dimensionblocker";

    private boolean netherLocked = true;
    private boolean endLocked = true;

    private long netherUnlockTime = 0L;
    private long endUnlockTime = 0L;

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

        if (!locked) {
            this.netherUnlockTime = 0L;
        }

        setDirty();
    }

    public void setEndLocked(boolean locked) {
        this.endLocked = locked;

        if (!locked) {
            this.endUnlockTime = 0L;
        }

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

        DimensionDataStorage storage =
                server.overworld().getDataStorage();

        return storage.computeIfAbsent(
                new SavedData.Factory<>(
                        DimensionBlockerData::new,
                        DimensionBlockerData::load
                ),
                DATA_NAME
        );
    }

    public DimensionBlockerData() {
    }

    public static DimensionBlockerData load(
            CompoundTag tag,
            HolderLookup.Provider provider
    ) {

        DimensionBlockerData data =
                new DimensionBlockerData();

        data.netherLocked =
                tag.getBoolean("NetherLocked");

        data.endLocked =
                tag.getBoolean("EndLocked");

        data.netherUnlockTime =
                tag.getLong("NetherUnlockTime");

        data.endUnlockTime =
                tag.getLong("EndUnlockTime");

        return data;
    }

    @Override
    public CompoundTag save(
            CompoundTag tag,
            HolderLookup.Provider provider
    ) {

        tag.putBoolean(
                "NetherLocked",
                netherLocked
        );

        tag.putBoolean(
                "EndLocked",
                endLocked
        );

        tag.putLong(
                "NetherUnlockTime",
                netherUnlockTime
        );

        tag.putLong(
                "EndUnlockTime",
                endUnlockTime
        );

        return tag;
    }
}