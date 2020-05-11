package com.mrmcscruffybeard.scruffysmachines.objects.tanks;

import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidTankTileEntityBase;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public interface IModFluidTank extends IFluidTank{

	void empty();
	
	boolean isFull();
	
	void setTankTileEntity(FluidTankTileEntityBase tileIn);
	
	boolean canHoldFluidType(FluidStack fluidStack);
	
	default boolean canHoldGas() { return false; };
}
