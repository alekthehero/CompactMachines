package dev.compactmods.machines.compat.jade.providers;

import dev.compactmods.machines.api.core.Constants;
import dev.compactmods.machines.api.core.Tooltips;
import dev.compactmods.machines.i18n.TranslationUtil;
import dev.compactmods.machines.machine.CompactMachineBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class CompactMachineProvider implements IBlockComponentProvider {
    public static final CompactMachineProvider INSTANCE = new CompactMachineProvider();

    @Override
    public void appendTooltip(
            ITooltip tooltip,
            BlockAccessor accessor,
            IPluginConfig config
    ) {
        final CompactMachineBlockEntity machine = (CompactMachineBlockEntity) accessor.getBlockEntity();
        machine.getConnectedRoom().ifPresentOrElse(room -> {
            tooltip.add(TranslationUtil.tooltip(Tooltips.Machines.BOUND_TO, room));
        }, () -> {
            MutableComponent newMachine = TranslationUtil
                    .message(new ResourceLocation(Constants.MOD_ID, "new_machine"))
                    .withStyle(ChatFormatting.GREEN);
            tooltip.add(newMachine);
        });
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(Constants.MOD_ID, "machine");
    }
}
