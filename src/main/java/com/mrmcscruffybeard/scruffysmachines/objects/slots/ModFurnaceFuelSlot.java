package com.mrmcscruffybeard.scruffysmachines.objects.slots;

import com.mrmcscruffybeard.scruffysmachines.objects.containers.BrickFurnaceContainer;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.BrickFurnaceTileEntity;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModFurnaceFuelSlot extends Slot {



	private ModFurnaceFuelSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);

	}

	public ModFurnaceFuelSlot(BrickFurnaceContainer containerIn, BrickFurnaceTileEntity tileEntityIn, int index, int xIn, int yIn) {

		this(tileEntityIn, index, xIn, yIn);
		
	}

	/**
	 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	 */
	@Override
	public boolean isItemValid(ItemStack stack) {
		return BrickFurnaceTileEntity.isFuel(stack) || isBucket(stack);
	}

	public static boolean isBucket(ItemStack stack) {
		return stack.getItem() == Items.BUCKET;
	}
	
	public int getItemStackLimit(ItemStack stack) {
	      return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
	   }
}
