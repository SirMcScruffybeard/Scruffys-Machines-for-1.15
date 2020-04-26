package com.mrmcscruffybeard.scruffysmachines.objects.slots;

import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.BrickFurnaceTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraftforge.fml.hooks.BasicEventHooks;

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

	@Override
	public boolean isItemValid(ItemStack stack) { return false; }

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new stack.
	 */
	public ItemStack decrStackSize(int amount) {

		if (this.getHasStack()) {

			this.removeCount += Math.min(amount, this.getStack().getCount());
		}

		return super.decrStackSize(amount);
	}

	@Override
	public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {

		this.onCrafting(stack);

		super.onTake(thePlayer, stack);

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
	@Override
	protected void onCrafting(ItemStack stack) {
		
		stack.onCrafting(this.player.world, this.player, this.removeCount);
		
		if (!this.player.world.isRemote && this.inventory instanceof AbstractFurnaceTileEntity) {
			
			((AbstractFurnaceTileEntity)this.inventory).func_213995_d(this.player);
			
		}

		this.removeCount = 0;
		
		BasicEventHooks.firePlayerSmeltedEvent(this.player, stack);
	}
}//ModFurnaceResultSlot
