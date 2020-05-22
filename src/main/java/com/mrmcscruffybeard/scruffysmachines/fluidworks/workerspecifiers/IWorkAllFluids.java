package com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers;

import net.minecraftforge.fluids.FluidStack;

public interface IWorkAllFluids extends IWorkerSpecifier {

	@Override
	default boolean canWorkLiquids() {
		
		return true;
	}
	
	@Override
	default boolean canWorkVapors() {
		
		return true;
	}
	
	@Override
	default boolean canWorkFluid(FluidStack rescoruce) {
		
		return true;
	}
}
