package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks;

import com.mrmcscruffybeard.scruffysmachines.util.helpers.FluidHelper;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IWaterTank extends ILiquidTank {

	@Override
	default boolean canConductFluid(Fluid fluid) {
		
		return FluidHelper.isWater(fluid);
	}
	
	@Override
	default boolean canConductFluid(FluidStack resource) {
		
		return this.canConductFluid(resource.getFluid());
	}
}
