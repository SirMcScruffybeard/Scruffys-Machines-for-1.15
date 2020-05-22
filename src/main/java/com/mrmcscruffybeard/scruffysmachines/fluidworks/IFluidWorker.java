package com.mrmcscruffybeard.scruffysmachines.fluidworks;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidWorker {

	Fluid getFluidType();

	boolean isEmpty();

	int empty();

	boolean isFull();

	int getFluidAmount();

	int getSpace();

	FluidStack getFluid();
	
	boolean isFluidValid(FluidStack resource);

	static boolean canAcceptTotalAmount(FluidWorkerTile worker, FluidStack resource) {
		
		return worker.getFluidAmount() >= resource.getAmount();
	}
	
	static boolean canAcceptFluid(FluidWorkerTile worker, FluidStack resource) {
		
		return !worker.isFull() && worker.isFluidValid(resource);
	}
	
	static int movefluid(FluidWorkerTile acceptWorker, FluidWorkerTile outWorker) {
		
		int moved = 0;
		
		if(canAcceptFluid(acceptWorker, outWorker.getFluid())) {
			
			
		}
		
		return moved;
	}
	
	static int getMoveAmount(FluidWorkerTile acceptWorker, FluidWorkerTile outWorker) {
		
		int space = acceptWorker.getSpace();
		
		int amount = outWorker.getFluidAmount();
		
		return space > amount ? space : amount;
	}
}
