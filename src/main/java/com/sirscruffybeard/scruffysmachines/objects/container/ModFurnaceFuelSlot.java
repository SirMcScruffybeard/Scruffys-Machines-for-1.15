package com.sirscruffybeard.scruffysmachines.objects.container;

import com.sirscruffybeard.scruffysmachines.objects.container.bases.FurnaceBaseContainer;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModFurnaceFuelSlot extends Slot {

	private final FurnaceBaseContainer furnace;

	public ModFurnaceFuelSlot(FurnaceBaseContainer furnaceIn, IInventory furnaceInventoryIn, int p_i50084_3_, int p_i50084_4_, int p_i50084_5_) {
		
		super(furnaceInventoryIn, p_i50084_3_, p_i50084_4_, p_i50084_5_);
		
		this.furnace = furnaceIn;
	}

	/**
	 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	 */
	public boolean isItemValid(ItemStack stack) {
		return this.furnace.isFuel(stack) || isBucket(stack);
	}

	public int getItemStackLimit(ItemStack stack) {
		return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
	}

	public static boolean isBucket(ItemStack stack) {
		return stack.getItem() == Items.BUCKET;
	}


}
