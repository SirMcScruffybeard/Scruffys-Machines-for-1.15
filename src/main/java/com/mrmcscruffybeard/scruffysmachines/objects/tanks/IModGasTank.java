package com.mrmcscruffybeard.scruffysmachines.objects.tanks;

import com.mrmcscruffybeard.scruffysmachines.objects.fluids.vapors.Vapor;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidTankTileEntityBase;

public interface IModGasTank extends IModFluidTank{

	
	@Override
	default boolean canHoldGas() { return true; }
	
	boolean canHoldGasType(Vapor vaporIn);
	
	void setTankTileEntity(FluidTankTileEntityBase tileIn);
}

