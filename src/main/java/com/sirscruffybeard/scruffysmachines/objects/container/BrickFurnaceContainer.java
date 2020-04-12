package com.sirscruffybeard.scruffysmachines.objects.container;

import com.sirscruffybeard.scruffysmachines.init.BlockInit;
import com.sirscruffybeard.scruffysmachines.init.ModContainerTypes;
import com.sirscruffybeard.scruffysmachines.objects.blocks.BrickFurnaceBlock;
import com.sirscruffybeard.scruffysmachines.objects.container.bases.FurnaceBaseContainer;
import com.sirscruffybeard.scruffysmachines.tileentity.BrickFurnaceTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;

public class BrickFurnaceContainer extends FurnaceBaseContainer{

	public static final String ID = BrickFurnaceBlock.ID;

	public BrickFurnaceContainer(int windowId, PlayerInventory playerInventoryIn, BrickFurnaceTileEntity tileEntityIn) {
		super(ModContainerTypes.BRICK_FURNACE.get(), IRecipeType.SMELTING, windowId, playerInventoryIn, tileEntityIn);
	}

	public BrickFurnaceContainer(int windowId, PlayerInventory playerInventoryIn, IInventory furnaceInventoryIn, IIntArray furnaceDataIn, BrickFurnaceTileEntity tileEntityIn) {
		super(ContainerType.FURNACE, IRecipeType.SMELTING, windowId, playerInventoryIn, furnaceInventoryIn, furnaceDataIn, tileEntityIn);
	}
	
	public BrickFurnaceContainer(final int windowId, final PlayerInventory playerInventoryIn, final PacketBuffer data) {
		
		this(windowId, playerInventoryIn, (BrickFurnaceTileEntity)getTileEntity(playerInventoryIn, data));
	}

	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.BRICK_FURNACE.get());
	}
}
