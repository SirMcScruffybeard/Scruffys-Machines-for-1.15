package com.sirscruffybeard.scruffysmachines.container;

import java.util.Objects;

import com.sirscruffybeard.scruffysmachines.init.ModContainerTypes;
import com.sirscruffybeard.scruffysmachines.tileentity.BrickFurnaceTileEntity;
import com.sirscruffybeard.scruffysmachines.tileentity.LeatherChestTileEntity;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;

public class BrickFurnaceContainer extends AbstractFurnaceContainer{

	private BrickFurnaceTileEntity tileEntity;

	private final int NUM_SLOTS = BrickFurnaceTileEntity.INVENTORY_SIZE;
	
	private final IWorldPosCallable canInteractWithCallable;
	
	IIntArray furnaceData;

	public BrickFurnaceContainer(final int windowId, PlayerInventory playerInventory, final BrickFurnaceTileEntity tileEntity) {
		super(ModContainerTypes.BRICK_FURNACE.get(), IRecipeType.SMELTING, windowId, playerInventory);
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
		
		
		int inX = 44;
				
		int inY = 24;
		
		int fuelY = 70;
		
		
		
		// Main Player Inventory
		int startPlayerInvX = 8;
		int startPlayerInvY = 102;
		int slotSizePlus2 = 18;
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startPlayerInvX + (column * slotSizePlus2),
						startPlayerInvY + (row * slotSizePlus2)));
			}
		}

		// Hotbar
		int hotbarY = startPlayerInvY + (startPlayerInvY / 2) + 7;
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, startPlayerInvX + (column * slotSizePlus2), hotbarY));
		}

	}

	private static BrickFurnaceTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {

		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");

		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());

		if(tileAtPos instanceof LeatherChestTileEntity ) {

			return(BrickFurnaceTileEntity)tileAtPos;
		}

		throw new IllegalStateException("Tile entity is not Correct" + tileAtPos);
	}//getTileEntity

	public BrickFurnaceContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {

		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}
	
	public BrickFurnaceContainer(final int windowId, PlayerInventory playerInventory, final BrickFurnaceTileEntity tileEntity, IIntArray furnaceData) {
		
		this(windowId, playerInventory, tileEntity);
		
		this.furnaceData = furnaceData;
		
	}




}//public class BrickFurnaceContainer
