package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.tileentities;

import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.FluidTank;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.IFluidTankHandler;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;

public class FluidTankTileEntity extends FluidWorkerTileEntity implements IFluidTankHandler{

	public FluidTankTileEntity(TileEntityType<?> tileEntityTypeIn, FluidTank tankIn) {
		
		super(tileEntityTypeIn, tankIn);
		
	}

	
	public int fill(FluidStack resource, FluidAction action, Direction dir, FluidTank tank) {
		
		return tank.fill(resource, action, dir);
	}

	
	public FluidStack drain(int maxDrain, FluidAction action, Direction dir, FluidTank tank) {

		return tank.drain(maxDrain, action, dir);
	}

	
	public FluidStack drain(FluidStack resource, FluidAction action, Direction dir, FluidTank tank) {

		return tank.drain(resource, action, dir);
	}
	
	public boolean isEmpty() {
		
		return this.isWorkerEmpty(worker);
	}

}
