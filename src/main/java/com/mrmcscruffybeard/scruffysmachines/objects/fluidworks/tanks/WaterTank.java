package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks;

import com.mrmcscruffybeard.scruffysmachines.util.helpers.FluidHelper;

import net.minecraftforge.fluids.FluidStack;

public class WaterTank extends FluidTank implements IWaterTank {

	public WaterTank(int capacityIn) {
		
		super(capacityIn);
	}
	
	@Override
	public boolean isFluidValid(FluidStack resource) {
		
		return super.isFluidValid(resource) && FluidHelper.isWater(resource);
	}

}
