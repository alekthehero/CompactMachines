package dev.compactmods.machines.room;

import com.google.common.base.Predicates;
import dev.compactmods.machines.api.room.history.RoomEntryPoint;
import dev.compactmods.machines.api.room.upgrade.components.RoomUpgradeList;
import dev.compactmods.machines.CMRegistries;
import dev.compactmods.machines.room.block.SolidWallBlock;
import dev.compactmods.machines.room.ui.preview.MachineRoomMenu;
import dev.compactmods.machines.room.ui.upgrades.RoomUpgradeMenu;
import dev.compactmods.machines.room.upgrade.NeoforgeRoomUpgradeInventory;
import dev.compactmods.machines.wall.BreakableWallBlock;
import dev.compactmods.machines.wall.ItemBlockWall;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.function.Supplier;

public interface Rooms {

  interface Blocks {
	 DeferredBlock<SolidWallBlock> SOLID_WALL = CMRegistries.BLOCKS.register("solid_wall", () ->
		  new SolidWallBlock(BlockBehaviour.Properties.of()
				.strength(-1.0F, 3600000.8F)
				.sound(SoundType.METAL)
				.lightLevel((state) -> 15)));


	 DeferredBlock<BreakableWallBlock> BREAKABLE_WALL = CMRegistries.BLOCKS.register("wall", () ->
		  new BreakableWallBlock(BlockBehaviour.Properties.of()
				.strength(3.0f, 128.0f)
				.requiresCorrectToolForDrops()));

	 static void prepare() {
	 }
  }

  interface Items {
	 Supplier<Item.Properties> WALL_ITEM_PROPS = Item.Properties::new;

	 DeferredItem<ItemBlockWall> ITEM_SOLID_WALL = CMRegistries.ITEMS.register("solid_wall", () ->
		  new ItemBlockWall(Blocks.SOLID_WALL.get(), WALL_ITEM_PROPS.get()));

	 DeferredItem<ItemBlockWall> BREAKABLE_WALL = CMRegistries.ITEMS.register("wall", () ->
		  new ItemBlockWall(Blocks.BREAKABLE_WALL.get(), WALL_ITEM_PROPS.get()));

	 static void prepare() {
	 }
  }

  interface Menus {
	 DeferredHolder<MenuType<?>, MenuType<MachineRoomMenu>> MACHINE_MENU = CMRegistries.CONTAINERS.register("machine",
		  () -> IMenuTypeExtension.create(MachineRoomMenu::createClientMenu));

	 DeferredHolder<MenuType<?>, MenuType<RoomUpgradeMenu>> ROOM_UPGRADES = CMRegistries.CONTAINERS.register("room_upgrades",
		  () -> IMenuTypeExtension.create(RoomUpgradeMenu::createClientMenu));

	 static void prepare() {
	 }
  }

  interface DataAttachments {
	 Supplier<AttachmentType<RoomEntryPoint>> LAST_ROOM_ENTRYPOINT = CMRegistries.ATTACHMENT_TYPES.register("last_entrypoint", () -> AttachmentType.builder(() -> RoomEntryPoint.INVALID)
		  .serialize(RoomEntryPoint.CODEC)
		  .build());

	 Supplier<AttachmentType<NeoforgeRoomUpgradeInventory>> UPGRADE_INV = CMRegistries.ATTACHMENT_TYPES.register("upgrades", () -> AttachmentType
		  .serializable(NeoforgeRoomUpgradeInventory::new)
		  .build());

	 Supplier<AttachmentType<GlobalPos>> OPEN_MACHINE_POS = CMRegistries.ATTACHMENT_TYPES.register("open_machine", () -> AttachmentType
		  .builder(() -> GlobalPos.of(Level.OVERWORLD, BlockPos.ZERO))
		  .serialize(GlobalPos.CODEC, Predicates.alwaysFalse())
		  .build());

	 Supplier<AttachmentType<RoomUpgradeList>> PERMANENT_UPGRADES = CMRegistries.ATTACHMENT_TYPES.register("permanent_upgrades", () -> AttachmentType
		 .builder(() -> new RoomUpgradeList(List.of()))
		 .serialize(RoomUpgradeList.CODEC)
		 .build());

	 static void prepare() {
	 }
  }

  static void prepare() {
	 Blocks.prepare();
	 Items.prepare();
	 Menus.prepare();
	 DataAttachments.prepare();
  }
}