package com.mrmcscruffybeard.scruffysmachines.objects.slots;

import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.BrickFurnaceTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ModFurnaceResultSlot extends Slot {

	private final PlayerEntity player;
	private int removeCount;

	public ModFurnaceResultSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, PlayerEntity playerIn) {
		super(inventoryIn, index, xPosition, yPosition);


		this.player = playerIn;
	}

	public ModFurnaceResultSlot(final PlayerEntity playerIn, final BrickFurnaceTileEntity tileEntityIn, int index, int xIn, int yIn) {

		this(tileEntityIn, index, xIn, yIn, playerIn);

	}

	public boolean isItemValidForSlot(ItemStack stack) { return false; }

	/********************************************************************************************************************
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new stack.
	 ********************************************************************************************************************/
	public ItemStack decrStackSize (int amount) {

		if(this.getHasStack()) {

			this.removeCount += Math.min(amount, this.getStack().getCount());	
		}

		return super.decrStackSize(amount);
	}

	public ItemStack onTake(PlayerEntity player, ItemStack stack) {

		this.onCrafting(stack);

		super.onTake(player, stack);

		return stack;
	}

	/*******************************************************************************************************************
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
	 * internal count then calls onCrafting(item).
	 *******************************************************************************************************************/
	protected void onCrafting(ItemStack stack, int amount) {

		this.removeCount += amount;

		this.onCrafting(stack);

	}

	/******************************************************************************************
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
	 ******************************************************************************************/
	protected void onCrafting(ItemStack stack) {
		
		stack.onCrafting(this.player.world, this.player, this.removeCount);
		
		if(!this.player.world.isRemote() && this.inventory instanceof BrickFurnaceTileEntity) {
			
			((BrickFurnaceTileEntity)this.inventory).processRecipe(player);
		}
		
		this.removeCount = 0;
		net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.player, stack);
	}
}//ModFurnaceResultSlot
