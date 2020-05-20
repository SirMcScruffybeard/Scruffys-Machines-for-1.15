package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks;

public interface ILiquidTank extends IFluidTank {

	@Override
	default boolean canConductVapors() {
		
		return false;
	}
}
