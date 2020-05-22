package com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraftforge.fluids.FluidStack;

public interface ILavaWorker extends IWorkerSpecifier {

	@Override
	default boolean canWorkLiquids() {
		
		return true;
	}
	
	@Override
	default boolean canWorkVapors() {
		
		return false;
	}
	
	static boolean isLava(FluidStack fluidStack) {

		return IWorkerSpecifier.isFluidType(fluidStack, Fluids.LAVA);
	}
	
	public static boolean isLava(Fluid fluid) {

		return fluid == Fluids.LAVA;
	}
}
