package com.sirscruffybeard.scruffysmachines.tileentity;

import com.sirscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.sirscruffybeard.scruffysmachines.objects.blocks.BrickFurnaceBlock;
import com.sirscruffybeard.scruffysmachines.tileentity.bases.FurnaceBaseTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

public class BrickFurnaceTileEntity extends FurnaceBaseTileEntity{

	public static final String ID = BrickFurnaceBlock.ID;
	

	
	public BrickFurnaceTileEntity() {

		super(ModTileEntityTypes.BRICK_FURNACE.get(), IRecipeType.SMELTING);
	}

	
}
