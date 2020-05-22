package com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IWorkerSpecifier {

	boolean canWorkLiquids();

	boolean canWorkVapors();

	boolean canWorkFluid(FluidStack rescoruce);

	static boolean isFluidType(final FluidStack fluidStack, final Fluid fluid) {

		return fluidStack.getFluid().getFluid() == fluid;
	}
}
