package com.sirscruffybeard.scruffysmachines.tileentity;

import com.sirscruffybeard.scruffysmachines.init.ModTileEntityTypes;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BrickFurnaceTileEntity extends AbstractFurnaceTileEntity{

	public BrickFurnaceTileEntity(TileEntityType<?> tileTypeIn,
			IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn) {
		super(tileTypeIn, recipeTypeIn);
		
	}
	
	public BrickFurnaceTileEntity() {
		
		this(ModTileEntityTypes.BRICK_FURNACE.get(), IRecipeType.SMELTING);
	}

	@Override
	 protected ITextComponent getDefaultName() {
	      return new TranslationTextComponent("container.brick_furnace");
	   }

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
