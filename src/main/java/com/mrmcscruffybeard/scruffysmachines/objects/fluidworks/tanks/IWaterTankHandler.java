package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks;

import com.mrmcscruffybeard.scruffysmachines.util.helpers.FluidHelper;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;

public interface IWaterTankHandler extends ILiquidTankHandler{

	@Override
	default boolean canConductFluid(Fluid fluid) {
		
		return FluidHelper.isWater(fluid);
	}
	
	static boolean isHandler(Object handler) {
		
		return handler instanceof IWaterTankHandler;
	}

	int fill(FluidStack resource, FluidAction action, Direction dir, WaterTank tank);
}
