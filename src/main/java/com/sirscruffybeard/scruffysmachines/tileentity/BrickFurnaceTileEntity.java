package com.sirscruffybeard.scruffysmachines.tileentity;

import com.sirscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.sirscruffybeard.scruffysmachines.objects.blocks.BrickFurnaceBlock;
import com.sirscruffybeard.scruffysmachines.objects.container.BrickFurnaceContainer;
import com.sirscruffybeard.scruffysmachines.tileentity.bases.FurnaceBaseTileEntity;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BrickFurnaceTileEntity extends FurnaceBaseTileEntity{

	public static final String ID = BrickFurnaceBlock.ID;



	public BrickFurnaceTileEntity() {

		super(ModTileEntityTypes.BRICK_FURNACE.get(), IRecipeType.SMELTING);
	}


	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.brick_furnace");
	}

	protected Container createMenu(int id, PlayerInventory player) {
		return new BrickFurnaceContainer(id, player, this, this.furnaceData, this);
	}
}
