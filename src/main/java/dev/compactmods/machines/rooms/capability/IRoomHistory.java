package dev.compactmods.machines.rooms.capability;

import dev.compactmods.machines.rooms.history.IRoomHistoryItem;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IRoomHistory extends INBTSerializable<ListNBT> {

    void clear();
    boolean hasHistory();
    IRoomHistoryItem peek();
    IRoomHistoryItem pop();

    void addHistory(IRoomHistoryItem item);
}
