package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks;

import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.IFluidWorker;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.IFluidWorkerHandler.FluidAction;

import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidTank extends IFluidWorker{

	int fill(FluidStack resource, FluidAction action, Direction dir);
	
	FluidStack drain(int maxDrain, FluidAction action,  Direction dir);
	
	FluidStack drain(FluidStack resource, FluidAction action,  Direction dir);
}
