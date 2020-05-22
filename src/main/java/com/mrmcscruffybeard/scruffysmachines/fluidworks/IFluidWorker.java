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

}
