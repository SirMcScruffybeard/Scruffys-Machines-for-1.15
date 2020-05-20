package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks;

import com.mrmcscruffybeard.scruffysmachines.objects.fluids.vapors.Vapor;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidWorkerHandler {

	enum FluidAction {

		EXECUTE, SIMULATE;

		public boolean execute() {

			return this == EXECUTE;
		}

		public boolean simulate() {

			return this == SIMULATE;
		}
	}
	
	default boolean isFull(FluidWorker worker) {

		return this.getSpaceInWorker(worker) == 0;
	}
	
	default boolean canConductVapors() {
		
		return true;
	}
	
	default boolean canConductFluids() {
		
		return true;
	}
	
	default boolean canConductFluid(Fluid fluid) {
		
		return canConductFluids();
	}
	
	default boolean canConductVapor(Vapor vapor) {
		
		return canConductVapors();
	}

	default int getWorkerCapacity(FluidWorker worker) {

		return worker.getCapacity();
	}

	default FluidStack getFluidInWorker(FluidWorker worker) {

		return worker.getFluid();
	}

	default Fluid getFluidTypeInWorker(FluidWorker worker) {

		return worker.getFluidType();
	}

	default void emptyWorker(FluidWorker worker) {

		worker.setFluid(FluidStack.EMPTY);
	}

	default boolean isWorkerEmpty(FluidWorker worker) {

		return worker.isEmpty();
	}

	default int getAmountInWorker(FluidWorker worker) {

		return worker.getAmount();
	}

	default int getSpaceInWorker(FluidWorker worker) {

		return worker.getSpace();
	}

	default int getWorkerId(FluidWorker worker) {

		return worker.getId();
	}

	default boolean isFluidValid(FluidWorker worker, FluidStack resource) {

		return worker.isFluidValid(resource);
	}

	int fill(FluidStack resource, FluidAction action, Direction dir, FluidWorker worker);

	FluidStack drain(int maxDrain, FluidAction action, Direction dir, FluidWorker worker);

	FluidStack drain(FluidStack resource, FluidAction action, Direction dir, FluidWorker worker);

}
