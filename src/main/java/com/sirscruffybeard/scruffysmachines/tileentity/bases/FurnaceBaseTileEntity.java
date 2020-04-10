package com.sirscruffybeard.scruffysmachines.tileentity.bases;

import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;

public abstract class FurnaceBaseTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {

	private static final int[] SLOTS_UP = new int[]{0};
	private static final int[] SLOTS_DOWN = new int[]{2, 1};
	private static final int[] SLOTS_HORIZONTAL = new int[]{1};

	private int burnTime;
	private int recipesUsed;
	private int cookTime;
	private int cookTimeTotal;

	protected final IIntArray furnaceData = new IIntArray() {
		public int get(int index) {
			switch(index) {
			case 0:
				return FurnaceBaseTileEntity.this.burnTime;
			case 1:
				return FurnaceBaseTileEntity.this.recipesUsed;
			case 2:
				return FurnaceBaseTileEntity.this.cookTime;
			case 3:
				return FurnaceBaseTileEntity.this.cookTimeTotal;
			default:
				return 0;
			}
		}

		public void set(int index, int value) {
			switch(index) {
			case 0:
				FurnaceBaseTileEntity.this.burnTime = value;
				break;
			case 1:
				FurnaceBaseTileEntity.this.recipesUsed = value;
				break;
			case 2:
				FurnaceBaseTileEntity.this.cookTime = value;
				break;
			case 3:
				FurnaceBaseTileEntity.this.cookTimeTotal = value;
			}

		}

		public int size() {
			return 4;
		}
	};

	protected FurnaceBaseTileEntity(TileEntityType<?> typeIn, IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn) {
		super(typeIn);

	}

}
