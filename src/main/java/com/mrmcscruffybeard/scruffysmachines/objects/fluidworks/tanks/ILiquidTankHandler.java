package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks;

public interface ILiquidTankHandler extends IFluidTankHandler{

	@Override
	default boolean canConductVapors() {
		
		return false;
	}
}