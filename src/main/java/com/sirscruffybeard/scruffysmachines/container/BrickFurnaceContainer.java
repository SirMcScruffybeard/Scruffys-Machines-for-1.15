package com.sirscruffybeard.scruffysmachines.container;

import java.util.Objects;

import com.sirscruffybeard.scruffysmachines.init.ModContainerTypes;
import com.sirscruffybeard.scruffysmachines.tileentity.BrickFurnaceTileEntity;
import com.sirscruffybeard.scruffysmachines.tileentity.LeatherChestTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class BrickFurnaceContainer extends Container {
	
	public BrickFurnaceContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		
		this(windowId, playerInventory, getTileEntity(playerInventory, data));

	}

	public BrickFurnaceContainer(int id, PlayerInventory player, BrickFurnaceTileEntity tileEntity) {
		
		
		
	}

	/********************************************************************************************************************************
	 * GetTileEntity
	 ********************************************************************************************************************************/
	private static BrickFurnaceTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
		
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		
		if(tileAtPos instanceof BrickFurnaceTileEntity ) {
			
			return(BrickFurnaceTileEntity)tileAtPos;
		}
		
		throw new IllegalStateException("Tile entity is not Correct" + tileAtPos);
	}//getTileEntity
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		// TODO Auto-generated method stub
		return false;
	}

}
