package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class FluidHelper {

	public static boolean isFluidType(final FluidStack fluidStack, final Fluid fluid) {

		return fluidStack.isFluidEqual(new FluidStack(fluid, 1));
	}

	public static boolean isWater(FluidStack fluidStack) {

		return isFluidType(fluidStack, Fluids.WATER);
	}
	
	public static boolean isLava(FluidStack fluidStack) {
		
		return isFluidType(fluidStack, Fluids.LAVA);
	}

}
