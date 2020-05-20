package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.tileentities;

import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.FluidWorkerTileEntity;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.FluidTank;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.IFluidTankHandler;

import net.minecraft.tileentity.TileEntityType;

/********************************************************************************************
 * FluidTankTileEntity
 * 
 * @author SirScruffyBeard
 *
 *	TileEntity that handles a single FluidTank
 ********************************************************************************************/
public class FluidTankTileEntity extends FluidWorkerTileEntity implements IFluidTankHandler{

	public FluidTankTileEntity(TileEntityType<?> tileEntityTypeIn, FluidTank tankIn) {

		super(tileEntityTypeIn, tankIn);

	}
	
	public boolean isEmpty() {
		
		return this.isWorkerEmpty(worker);
	}

}
