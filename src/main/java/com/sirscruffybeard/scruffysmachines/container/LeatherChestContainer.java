package com.sirscruffybeard.scruffysmachines.container;

import java.util.Objects;

import com.sirscruffybeard.scruffysmachines.init.BlockInit;
import com.sirscruffybeard.scruffysmachines.init.ModContainerTypes;
import com.sirscruffybeard.scruffysmachines.tileentity.LeatherChestTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

public class LeatherChestContainer extends Container{
	
	public final LeatherChestTileEntity tileEntity;
	
	private final int NUM_SLOTS = LeatherChestTileEntity.INVENTORY_SIZE;
	
	private final IWorldPosCallable canInteractWithCallable;
	
	public LeatherChestContainer(final int windowId, final PlayerInventory playerInventory, final LeatherChestTileEntity tileEntity) {
		super(ModContainerTypes.LEATHER_CHEST.get(), windowId);
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
		
		// Main Inventory
				int startX = 12;
				int startY = 25;
				int slotSizePlus2 = 23;
				for (int row = 0; row < 4; ++row) {
					for (int column = 0; column < 9; ++column) {
						this.addSlot(new Slot(tileEntity, (row * 9) + column, startX + (column * slotSizePlus2),
								startY + (row * slotSizePlus2)));
					}
				}

				// Main Player Inventory
				int startPlayerInvY = startY * 5 + 9;
				for (int row = 0; row < 3; ++row) {
					for (int column = 0; column < 9; ++column) {
						this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2),
								startPlayerInvY + (row * slotSizePlus2)));
					}
				}

				// Hotbar
				int hotbarY = startPlayerInvY + (startPlayerInvY / 2) + 7;
				for (int column = 0; column < 9; ++column) {
					this.addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), hotbarY));
				}
	}
	
	private static LeatherChestTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
		
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		
		if(tileAtPos instanceof LeatherChestTileEntity ) {
			
			return(LeatherChestTileEntity)tileAtPos;
		}
		
		throw new IllegalStateException("Tile entity is not Correct" + tileAtPos);
	}//getTileEntity
	
	//Constructor
	public LeatherChestContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		
		return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.LEATHER_CHEST.get());
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) {
			
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();
			
			if(index < this.NUM_SLOTS) {
				
				if(!this.mergeItemStack(itemStack1, this.NUM_SLOTS, this.inventorySlots.size(), true)) {
					
					return ItemStack.EMPTY;
				}
				
			} else if(this.mergeItemStack(itemStack1, 0, NUM_SLOTS, false)) {
				
				return ItemStack.EMPTY;
			}
			
			if(itemStack1.isEmpty()) {
				
				slot.putStack(ItemStack.EMPTY);
			}else {
				
				slot.onSlotChanged();
			}
		}
		
		return itemStack;
	}
		
		
}//LeatherChestContainer
