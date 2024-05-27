package dev.compactmods.machines.machine;

import dev.compactmods.machines.api.item.component.MachineComponents;
import dev.compactmods.machines.api.machine.MachineConstants;
import dev.compactmods.machines.api.room.RoomComponents;
import dev.compactmods.machines.api.room.RoomTemplate;
import dev.compactmods.machines.CMRegistries;
import dev.compactmods.machines.machine.block.BoundCompactMachineBlock;
import dev.compactmods.machines.machine.block.BoundCompactMachineBlockEntity;
import dev.compactmods.machines.machine.block.UnboundCompactMachineBlock;
import dev.compactmods.machines.machine.block.UnboundCompactMachineEntity;
import dev.compactmods.machines.machine.item.BoundCompactMachineItem;
import dev.compactmods.machines.machine.item.UnboundCompactMachineItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.IntTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface Machines {
    // TODO: Metal material replacement
    BlockBehaviour.Properties MACHINE_BLOCK_PROPS = BlockBehaviour.Properties
            .of()
            .strength(8.0F, 20.0F)
            .requiresCorrectToolForDrops();

    Supplier<Item.Properties> MACHINE_ITEM_PROPS = Item.Properties::new;

    interface Blocks {
        DeferredBlock<UnboundCompactMachineBlock> UNBOUND_MACHINE = CMRegistries.BLOCKS.register("new_machine", () ->
                new UnboundCompactMachineBlock(MACHINE_BLOCK_PROPS));

        DeferredBlock<BoundCompactMachineBlock> BOUND_MACHINE = CMRegistries.BLOCKS.register("machine", () ->
                new BoundCompactMachineBlock(MACHINE_BLOCK_PROPS));

        static void prepare(){}
    }

    interface Items {
        DeferredItem<BoundCompactMachineItem> BOUND_MACHINE = CMRegistries.ITEMS.register("machine",
                () -> new BoundCompactMachineItem(MACHINE_ITEM_PROPS.get()));

        DeferredItem<UnboundCompactMachineItem> UNBOUND_MACHINE = CMRegistries.ITEMS.register("new_machine",
                () -> new UnboundCompactMachineItem(MACHINE_ITEM_PROPS.get()));

        static void prepare(){}
    }

    interface BlockEntities {

        DeferredHolder<BlockEntityType<?>, BlockEntityType<UnboundCompactMachineEntity>> UNBOUND_MACHINE = CMRegistries.BLOCK_ENTITIES.register(MachineConstants.UNBOUND_MACHINE_ENTITY.getPath(), () ->
                BlockEntityType.Builder.of(UnboundCompactMachineEntity::new, Blocks.UNBOUND_MACHINE.get())
                        .build(null));

        DeferredHolder<BlockEntityType<?>, BlockEntityType<BoundCompactMachineBlockEntity>> MACHINE = CMRegistries.BLOCK_ENTITIES.register(MachineConstants.BOUND_MACHINE_ENTITY.getPath(), () ->
                BlockEntityType.Builder.of(BoundCompactMachineBlockEntity::new, Blocks.BOUND_MACHINE.get())
                        .build(null));

        static void prepare(){}
    }

    interface DataComponents {
        DeferredHolder<DataComponentType<?>, DataComponentType<String>> BOUND_ROOM_CODE = CMRegistries.DATA_COMPONENTS
                .registerComponentType("room_code", MachineComponents.BOUND_ROOM_CODE);

        DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MACHINE_COLOR = CMRegistries.DATA_COMPONENTS
                .registerComponentType("machine_color", MachineComponents.MACHINE_COLOR);

        DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> ROOM_TEMPLATE_ID = CMRegistries.DATA_COMPONENTS
                .registerComponentType("room_template_id", MachineComponents.ROOM_TEMPLATE_ID);

        DeferredHolder<DataComponentType<?>, DataComponentType<RoomTemplate>> ROOM_TEMPLATE = CMRegistries.DATA_COMPONENTS
                .registerComponentType("room_template", RoomComponents.ROOM_TEMPLATE);

        static void prepare(){}
    }

    interface Attachments {
        Supplier<AttachmentType<Integer>> MACHINE_COLOR = CMRegistries.ATTACHMENT_TYPES.register("machine_color", () -> AttachmentType
                .builder(() -> 0xFFFFFFFF)
                .serialize(new IAttachmentSerializer<IntTag, Integer>() {
                    @Override
                    public Integer read(IAttachmentHolder holder, IntTag tag, HolderLookup.Provider provider) {
                        return tag.getAsInt();
                    }

                    @Override
                    public @Nullable IntTag write(Integer attachment, HolderLookup.Provider provider) {
                        return IntTag.valueOf(attachment);
                    }
                })
                .build());

        static void prepare(){}
    }

    static void prepare() {
        Blocks.prepare();
        Items.prepare();
        BlockEntities.prepare();
        DataComponents.prepare();
        Attachments.prepare();
    }
}