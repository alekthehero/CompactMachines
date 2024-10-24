package dev.compactmods.machines.compat.jade;

import dev.compactmods.machines.compat.jade.providers.CompactMachineProvider;
import dev.compactmods.machines.machine.CompactMachineBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(CompactMachineProvider.INSTANCE, CompactMachineBlock.class);
    }
}
