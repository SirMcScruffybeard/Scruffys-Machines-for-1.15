package com.mrmcscruffybeard.scruffysmachines.objects.containers;

import java.util.Objects;

import com.mrmcscruffybeard.scruffysmachines.init.BlockInit;
import com.mrmcscruffybeard.scruffysmachines.init.ModContainerTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.slots.ModFurnaceFuelSlot;
import com.mrmcscruffybeard.scruffysmachines.objects.slots.ModFurnaceResultSlot;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.BrickFurnaceTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;

public class BrickFurnaceContainer extends Container {

	public static final String ID = BrickFurnaceTileEntity.ID;

	BrickFurnaceTileEntity tileEntity;
	
	final int NUM_SLOTS = BrickFurnaceTileEntity.INVENTORY_SIZE;

	private final IWorldPosCallable canInteractWithCallable;

	private final IRecipeType<? extends AbstractCookingRecipe> recipeType = IRecipeType.SMELTING;
	
	private final LazyOptional<IItemHandlerModifiable> itemHandler;


	/***********************************************************************************************************************************
	 * BrickFurnaceContainer()
	 * @param windowId
	 * @param playerInventoryIn
	 * @param tileEntity
	 ***********************************************************************************************************************************/
	public BrickFurnaceContainer(final int windowId, final PlayerInventory playerInventoryIn, final BrickFurnaceTileEntity tileEntityIn) {
		super(ModContainerTypes.BRICK_FURNACE.get(), windowId);

		this.tileEntity = tileEntityIn;

		this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
		
		this.itemHandler = tileEntityIn.getItemHandler();
		
		int inX = 26;
		int inY = 16;
		int inIndex = 0;
		this.addSlot(new Slot(tileEntityIn, inIndex, inX, inY));
		
		int fuelX = 26;
		int fuelY = 61;
		int fuelIndex = 1;
		this.addSlot(new ModFurnaceFuelSlot(this, tileEntityIn, fuelIndex, fuelX, fuelY));
		
		int outX = 134;
		int outY = 39;
		int outputIndex = 2;
		this.addSlot(new ModFurnaceResultSlot(playerInventoryIn.player, tileEntityIn, outputIndex, outX, outY));
		
		int startX = 8;
		int startY = 102;
		int slotSizePlus2 = 16;
		
		// Main Player Inventory
		int startPlayerInvY = startY;
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventoryIn, 9 + (row * 9) + column, startX + (column * slotSizePlus2),
						startPlayerInvY + (row * slotSizePlus2)));
			}
		}

		// Hotbar
		int hotbarY = startPlayerInvY + (startPlayerInvY / 2) + 7;
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventoryIn, column, startX + (column * slotSizePlus2), hotbarY));
		}
	}

	/*******************************************************************************************************************
	 * BrickFurnaceContainer()
	 * @param windowId
	 * @param playerInventoryIn
	 * @param data
	 *******************************************************************************************************************/
	public BrickFurnaceContainer(final int windowId, final PlayerInventory playerInventoryIn, final PacketBuffer data) {

		this(windowId, playerInventoryIn, getTileEntity(playerInventoryIn, data));
	}

	/********************************************************************************************************************************
	 * GetTileEntity
	 ********************************************************************************************************************************/
	private static BrickFurnaceTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {

		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");

		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());

		if(tileAtPos instanceof BrickFurnaceTileEntity) {

			return(BrickFurnaceTileEntity)tileAtPos;
		}

		throw new IllegalStateException("Tile entity is not Correct" + tileAtPos);
	}//getTileEntity

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {

		return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.BRICK_FURNACE.get());
	}//CanInteractWith

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
	
	 public void fillStackedContents(RecipeItemHelper itemHelperIn) {
	      if (this.itemHandler instanceof IRecipeHelperPopulator) {
	         ((IRecipeHelperPopulator)this.itemHandler).fillStackedContents(itemHelperIn);
	      }

	   }
}
