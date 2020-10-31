package com.robotgryphon.compactmachines.item;

import com.robotgryphon.compactmachines.CompactMachines;
import com.robotgryphon.compactmachines.core.Registrations;
import com.robotgryphon.compactmachines.util.CompactMachineUtil;
import com.robotgryphon.compactmachines.util.PlayerUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPersonalShrinkingDevice extends Item {

    public ItemPersonalShrinkingDevice(Properties props) {
        super(props);
    }

//    TODO Model locations
//    @SideOnly(MixinEnvironment.Side.CLIENT)
//    public void initModel() {
//        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
//    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if (Screen.hasShiftDown()) {
            tooltip.add(
                    new TranslationTextComponent("tooltip." + CompactMachines.MODID + ".psd.hint")
                            .mergeStyle(TextFormatting.YELLOW));
        } else {
            tooltip.add(
                    new TranslationTextComponent("tooltip." + CompactMachines.MODID + ".hold_shift.hint")
                            .mergeStyle(TextFormatting.GRAY));
        }

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (hand == Hand.OFF_HAND) {
            return ActionResult.resultFail(stack);
        }

//        TODO: Machine enter/exit dimension
//        if(world.provider.getDimension() != ConfigurationHandler.Settings.dimensionId) {
//            // player.openGui(compactmachines.instance, GuiIds.PSD_GUIDE.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
//            return new ActionResult(ActionResultType.SUCCESS, stack);
//        }
//
        if (!world.isRemote && player instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

            if (serverPlayer.world.getDimensionKey() == Registrations.COMPACT_DIMENSION) {
                if (player.isSneaking()) {
                    ServerWorld w = serverPlayer.getServerWorld();
                    CompactMachineUtil.setMachineSpawn(w.getServer(), player.getPosition());

                    IFormattableTextComponent tc = new TranslationTextComponent("messages.compactmachines.psd.spawnpoint_set")
                            .mergeStyle(TextFormatting.GREEN);

                    player.sendStatusMessage(tc, true);
                } else {
                    PlayerUtil.teleportPlayerOutOfMachine(world, serverPlayer);
                }
            }
        }

        return ActionResult.resultSuccess(stack);
    }

}
