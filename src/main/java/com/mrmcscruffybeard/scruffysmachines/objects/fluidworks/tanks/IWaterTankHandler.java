package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks;

import com.mrmcscruffybeard.scruffysmachines.util.helpers.FluidHelper;

import net.minecraft.fluid.Fluid;

public interface IWaterTankHandler extends ILiquidTankHandler{

	@Override
	default boolean canConductFluid(Fluid fluid) {
		
		return FluidHelper.isWater(fluid);
	}
}
